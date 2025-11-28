package com.demo.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Member;
import com.demo.repository.MemberRepository;
import com.demo.Service.EmailService;
import com.demo.util.JwtUtil;

@RestController
public class MemberController {
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	private EmailService emailService;
	
	//註冊會員
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Member member){
		String memberid=memRepo.queryNextMemberId();
		int num=Integer.parseInt(memberid.substring(1,6));
		String nextMember_id = String.format("m%05d",num+1);
		member.setMemberId(nextMember_id);
		if(memRepo.findByEmail(member.getEmail())!=null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("eamil已註冊");
		} else if(memRepo.findByIdNumber(member.getIdNumber())!=null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("身分證字號已註冊");
		} else if(memRepo.findByPhone(member.getPhone())!=null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("手機已註冊");
		} else {
			Member mem=memRepo.save(member);
			return ResponseEntity.ok(mem);
		}
	}
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	//會員登入
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username,@RequestParam String password){
		
	    Member mem1 = memRepo.findByIdNumberAndPassword(username, password);
	    Member mem2 = memRepo.findByEmailAndPassword(username, password);

	    Member member = mem1 != null ? mem1 : mem2;

	    if (member != null) {
	        String token = jwtUtil.generateToken(member.getIdNumber()); // 用身分證或 email 代表身份

	        Map<String, Object> result = new HashMap<>();
	        result.put("token", token);
	        result.put("member", member);

	        return ResponseEntity.ok(result);
	    } else {
	        return ResponseEntity.status(401).body("帳號或密碼錯誤");
	    }
	}
	
	@GetMapping("/allmembers")
	public ResponseEntity<List<Member>> getAll(){
		List<Member> memList=memRepo.findAll();
		
		if(memList.size()>0) {
			return ResponseEntity.ok(memList);
		} else return ResponseEntity.noContent().build();
	}
	
	
	//依條件查詢會員
	@GetMapping("/searchmember")
	public List<Member> searchMember(
	        @RequestParam(required = false) String membername,
	        @RequestParam(required = false) String idnumber,
	        @RequestParam(required = false) String phone,
	        @RequestParam(required = false) String memberid) {

	    membername = (membername == null || membername.trim().isEmpty()) ? null : membername.trim();
	    idnumber = (idnumber == null || idnumber.trim().isEmpty()) ? null : idnumber.trim();
	    phone = (phone == null || phone.trim().isEmpty()) ? null : phone.trim();
	    memberid = (memberid == null || memberid.trim().isEmpty()) ? null : memberid.trim();

	    return memRepo.search(membername, idnumber, phone, memberid);
	}
	
	//變更會員資料
	@PutMapping("/updatemember")
	public ResponseEntity<Member> updateMember(@RequestBody Member mem){
		Member foundMem=memRepo.findByMemberId(mem.getMemberId());
		if(foundMem !=null) {
			foundMem.setAddress(mem.getAddress());
			foundMem.setEmail(mem.getEmail());
			memRepo.save(foundMem);
			return ResponseEntity.ok(foundMem);
		}
		
		else return ResponseEntity.notFound().build();
	}
	
	//會員資料--後台
	@GetMapping("/showmember/{memberId}")
	public ResponseEntity<Member> updateMemberByEmp(@PathVariable("memberId") String memberId){
		Member foundMem=memRepo.findByMemberId(memberId);
		if(foundMem !=null) {
			return ResponseEntity.ok(foundMem);
		}
		else return ResponseEntity.notFound().build();
	}
	
	//變更會員密碼
	@PutMapping("/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestParam String memid,@RequestParam String oldpw, @RequestParam String newpw){
		Member foundMem=memRepo.findByMemberId(memid);
		if(foundMem.getPassword().equals(oldpw)) {
			foundMem.setPassword(newpw);
			memRepo.save(foundMem);
			return ResponseEntity.ok(foundMem);
		}
		else return ResponseEntity.badRequest().body("密碼輸入錯誤");
	}
	
    // 驗證 Token
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(value="Authorization", required=false) String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // 去掉 "Bearer " 前綴
        }

        if (token != null && jwtUtil.validateToken(token)) {
            return ResponseEntity.ok(Map.of("valid", true, "message", "Token 有效"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Token 無效或已過期"));
        }
    }

    // 範例：會員中心保護資源
    @GetMapping("/protected/resource")
    public ResponseEntity<?> getProtectedResource(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            return ResponseEntity.ok(Map.of(
                "message", "這是受保護的資料",
                "user", username,
                "timestamp", new Date()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
    
    // 忘記密碼 → 寄信
    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestParam String email) {
        Member member = memRepo.findByEmail(email);
        if (member == null) return ResponseEntity.badRequest().body("Email 未註冊");

        // 產生 token
        String token = UUID.randomUUID().toString();
        member.setResetToken(token);
        member.setTokenExpire(LocalDateTime.now().plusMinutes(30));
        memRepo.save(member);

        // 記得加上 port (假設 8080)
        String resetUrl = "http://localhost:8080/reset.html?token=" + token + "&email=" + email;

        // HTML 信件內容
        String htmlContent = "<p>請點擊以下連結重設密碼 (30分鐘有效)：</p>"
                           + "<a href=\"" + resetUrl + "\">點此重設密碼</a>";

        // 寄送 HTML 郵件
        emailService.sendHtml(email, "重設密碼", htmlContent);

        return ResponseEntity.ok("重設密碼信已寄出");
    }

    // 驗證 token
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestParam String email, @RequestParam String token) {
        Member member = memRepo.findByEmail(email);
        if (member == null) return ResponseEntity.badRequest().body("Email 錯誤");

        if (member.getTokenExpire() == null || member.getTokenExpire().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token 已過期");
        }

        if (!token.equals(member.getResetToken())) {
            return ResponseEntity.badRequest().body("Token 無效");
        }

        return ResponseEntity.ok("驗證成功");
    }

    // 重設密碼
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String token,
                                           @RequestParam String newPassword) {
        Member member = memRepo.findByEmail(email);
        if (member == null) return ResponseEntity.badRequest().body("Email 錯誤");

        if (!token.equals(member.getResetToken()) || member.getTokenExpire().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token 無效或已過期");
        }

        member.setPassword(newPassword);
        member.setResetToken(null);
        member.setTokenExpire(null);
        memRepo.save(member);

        return ResponseEntity.ok("密碼重設成功");
    }

}
