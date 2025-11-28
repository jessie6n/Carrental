package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.model.Order;
import com.demo.repository.OrderRepository;

@Controller
public class OrderDetailController {

	@Autowired
	OrderRepository orderRepo;

	@GetMapping("/order/{id}")
	public String orderDetail(@PathVariable Integer id, Model model) {

		// ★★★ 未來 JWT 整合點：檢查訂單權限 ★★★
		Order order = orderRepo.findById(id).orElse(null);

		if (order == null) {
			return "redirect:/my-orders";
		}

		model.addAttribute("order", order);

		return "my-order-detail";
	}
}