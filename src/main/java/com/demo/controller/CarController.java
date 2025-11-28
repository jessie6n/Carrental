package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Car;
import com.demo.repository.CarRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CarController {
	@Autowired
	private CarRepository carRepo;

	@GetMapping("/car")
	public ResponseEntity<List<Car>> getAll() {
		List<Car> listData = carRepo.findAll();

		return ResponseEntity.ok(listData);
	}
}