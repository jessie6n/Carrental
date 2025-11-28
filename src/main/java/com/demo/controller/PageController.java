package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.Service.BranchService;
import com.demo.model.Branch;
import com.demo.model.Member;
import com.demo.repository.MemberRepository;
import com.demo.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	@Autowired
	BranchService branchService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	MemberRepository memberRepo;

	// 首頁
	@GetMapping("/")
	public String home() {
		return "index";
	}

	// ⭐ 修改：接收 token 參數
	@GetMapping("/reserve")
	public String showReservePage(@RequestParam(required = false) String token, Model model, HttpSession session) {

		// 1. 檢查 Session 是否已經有登入資訊 (避免重新整理頁面時又要傳 Token)
		String loggedInUser = (String) session.getAttribute("loginUserId");

		if (loggedInUser == null) {
			// 2. Session 沒資料，檢查傳進來的 Token
			if (token != null && jwtUtil.validateToken(token)) {
				// 3. Token 有效 -> 解析身分證字號 (Subject)
				String idNumber = jwtUtil.getUsernameFromToken(token);

				// 4. 查詢資料庫取得 Member ID (業務主鍵 e.g. m00001)
				Member member = memberRepo.findByIdNumber(idNumber);

				if (member != null) {
					// ⭐ 關鍵：將會員 ID 寫入 Session，後續步驟都靠它認人
					session.setAttribute("loginUserId", member.getMemberId());
					session.setAttribute("loginUserName", member.getName());
				} else {
					// 找不到人 (怪怪的)，踢回首頁
					return "redirect:/index.html";
				}
			} else {
				// 5. 沒 Token 或 Token 無效 -> 踢回前端首頁請他登入
				return "redirect:/index.html";
			}
		}

		// 6. 驗證通過，載入分店資料並顯示頁面
		List<Branch> branches = branchService.findAll();
		model.addAttribute("branches", branches);
		return "reserve";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 清除後端 Session
		session.invalidate();
		// 導回前端首頁 (5500)
		return "redirect:/index.html";
	}
}