package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.model.Order;
import com.demo.repository.OrderRepository;

@Controller
public class OrderController {

	@Autowired
	OrderRepository orderRepo;

	@GetMapping("/my-orders")
	public String myOrders(@RequestParam(required = false) String memberId, Model model) {

		List<Order> orders;

		if (memberId != null && !memberId.isEmpty()) {
			// ⭐ 有傳 memberId 就查該會員的訂單
			orders = orderRepo.findByMemberId(memberId);
		} else {
			// 沒傳 memberId (例如直接打網址)，顯示空列表或是全部 (視需求而定)
			// 建議顯示空列表，避免資料外洩
			orders = new ArrayList<>();
		}

		model.addAttribute("orders", orders);

		return "my-orders";
	}

	@GetMapping("/order/cancel/{id}")
	public String cancelOrder(@PathVariable Integer id) {

		// ★★★ 未來 JWT 整合點：檢查訂單權限 ★★★
		Order order = orderRepo.findById(id).orElse(null);
		String memberId = null; // 用來暫存 memberId
		if (order != null) {
			memberId = order.getMemberId(); // 先把 ID 存起來
			order.setStatus("已取消");
			orderRepo.save(order);
		}

		// ⭐ 修改：重導時帶上 memberId 參數
		if (memberId != null) {
			return "redirect:/my-orders?memberId=" + memberId;
		} else {
			return "redirect:/my-orders";
		}
	}
}