package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Service.DriverOrderService;
import com.demo.model.DriverOrder;
import com.demo.model.Member;
import com.demo.repository.MemberRepository;
import com.demo.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class DriverOrderController {

	private final DriverOrderService driverOrderService;

	// ⭐ 新增：注入 MemberRepository 以查詢會員資料
	private final MemberRepository memberRepo;

	@Autowired
	private JwtUtil jwtUtil;

	// 建立新訂單 (加入權限驗證與會員綁定)
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody DriverOrder dorder,
			@RequestHeader(value = "Authorization", required = false) String authHeader) {

		// 1. 檢查 Token 是否存在
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入會員");
		}

		// 2. 驗證 Token
		String token = authHeader.substring(7);
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入逾時，請重新登入");
		}

		// 3. 解析 Token 並查詢真實會員 ID
		try {
			// Token 內存的是身分證字號 (Subject)
			String idNumber = jwtUtil.getUsernameFromToken(token);

			// 查詢資料庫確認會員存在
			Member member = memberRepo.findByIdNumber(idNumber);

			if (member == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("會員資料不存在");
			}

			// ⭐ 關鍵修正：將正確的會員編號 (例如 m00005) 寫入訂單
			dorder.setMemberId(member.getMemberId());

			// 為了資料完整性，也可以順便把姓名電話強制覆寫為會員資料 (看需求，這裡先不覆寫以保留表單輸入)
			// dorder.setName(member.getName());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("驗證失敗");
		}

		// 4. 建立訂單
		try {
			DriverOrder saved = driverOrderService.createDorder(dorder);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("建立訂單失敗：" + e.getMessage());
		}
	}

	// 依電話查詢訂單
	@GetMapping("/phone/{phone}")
	public ResponseEntity<?> getOrdersByPhone(@PathVariable String phone) {
		List<DriverOrder> list = driverOrderService.findDorderByPhone(phone);
		if (list.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(list);
	}

	// 查詢所有訂單
	@GetMapping("/getall")
	public ResponseEntity<?> getAllOrders() {
		return ResponseEntity.ok(driverOrderService.findAll());
	}

	// 依 ID 查詢訂單
	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable Long id) {
		DriverOrder order = driverOrderService.findById(id);
		if (order == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(order);
	}
	
	// 依 memberID 查詢訂單
	@GetMapping("/memberid")
	public ResponseEntity<?> getOrderByMemberId(@RequestParam("memid") String memid) {
	    List<DriverOrder> orderList = driverOrderService.findByMemberId(memid);
	    if (orderList.size() == 0) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(orderList);
	}

	// 依 orderNo 查詢
	@GetMapping("/find/orderNo/{orderNo}")
	public ResponseEntity<?> getOrderByOrderNo(@PathVariable String orderNo) {
		DriverOrder order = driverOrderService.findByOrderNo(orderNo);

		if (order == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("查無此訂單：" + orderNo);
		}

		return ResponseEntity.ok(order);
	}
}