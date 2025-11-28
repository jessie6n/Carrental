package com.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Service.SubscriptionOrderService;
import com.demo.model.Member;
import com.demo.model.SubscriptionOrder;
import com.demo.repository.MemberRepository;
import com.demo.util.JwtUtil;

@RestController
@RequestMapping("/api/sub-orders") // ⭐ 修改路徑，避免衝突
public class SubscriptionOrderController {

	@Autowired
	private SubscriptionOrderService service;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MemberRepository memberRepo;

	// 自動產生訂單編號
	private String generateOrderNo() {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return "SUB" + date + System.currentTimeMillis(); // 使用 SUB 前綴
	}

	// 新增訂單（JWT 驗證）
	@PostMapping
	public ResponseEntity<?> create(@RequestBody SubscriptionOrder order,
			@RequestHeader(value = "Authorization", required = false) String authHeader) {

		// 1. JWT 驗證
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入會員");
		}
		String token = authHeader.substring(7);
		if (!jwtUtil.validateToken(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入逾時，請重新登入");
		}

		// 2. 解析 Member ID
		String idNumber = jwtUtil.getUsernameFromToken(token);
		Member member = memberRepo.findByIdNumber(idNumber);
		if (member == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("會員資料不存在");
		}
		order.setMemberId(member.getMemberId()); // ⭐ 自動綁定會員

		// 3. 必填欄位檢查
		if (order.getCarId() == null || order.getStore() == null || order.getStore().isEmpty()
				|| order.getStartDate() == null || order.getStartDate().isEmpty() || order.getStartTime() == null
				|| order.getStartTime().isEmpty() || order.getMonths() == null) {
			return ResponseEntity.badRequest().body("欄位不能為空白");
		}

		// 4. 產生單號與狀態
		order.setOrderNo(generateOrderNo());
		autoSetOrderStatus(order);

		// 5. 存檔
		SubscriptionOrder saved = service.createOrder(order);
		return ResponseEntity.ok(saved);
	}

	// 自動設定狀態 (邏輯保留)
	private void autoSetOrderStatus(SubscriptionOrder o) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(o.getStartDate());
			// 概算結束日期 (30天/月)
			long endTime = start.getTime() + (long) o.getMonths() * 30L * 24 * 60 * 60 * 1000;
			Date end = new Date(endTime);

			if (new Date().before(end)) {
				o.setStatus("進行中");
			} else {
				o.setStatus("已完成");
			}
		} catch (Exception e) {
			o.setStatus("進行中");
		}
	}

	// 查所有訂單 (管理員用)
	@GetMapping
	public List<SubscriptionOrder> getAll() {
		return service.getAllOrders();
	}

	// 查會員自己的訂單 (需驗證 JWT)
	@GetMapping("/my-orders")
	public ResponseEntity<?> getMyOrders(@RequestHeader(value = "Authorization", required = false) String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			return ResponseEntity.status(401).build();
		String token = authHeader.substring(7);
		if (!jwtUtil.validateToken(token))
			return ResponseEntity.status(401).build();

		String idNumber = jwtUtil.getUsernameFromToken(token);
		Member member = memberRepo.findByIdNumber(idNumber);

		List<SubscriptionOrder> list = service.getOrdersByMemberId(member.getMemberId());
		list.forEach(this::autoSetOrderStatus); // 更新狀態
		// 這裡不一定要 saveAll，因為狀態可能是動態計算的，看需求

		return ResponseEntity.ok(list);
	}
}