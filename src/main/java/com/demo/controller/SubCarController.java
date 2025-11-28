package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Service.SubCarService;
import com.demo.model.SubCar;

@RestController
@RequestMapping("/api/subcars")
@CrossOrigin
public class SubCarController {

	@Autowired
	private SubCarService service;

	// 取得所有訂閱車款
	@GetMapping
	public ResponseEntity<List<SubCar>> getAllCars() {
		return ResponseEntity.ok(service.getAllCars());
	}

	// 取得單一車款 (前端選車詳情頁用)
	@GetMapping("/{id}")
	public ResponseEntity<SubCar> getCarById(@PathVariable Integer id) {
		SubCar car = service.getCarById(id);
		if (car == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(car);
	}
}