package com.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.SubCar; // 引用改名後的 Model
import com.demo.repository.SubCarRepository; // 引用改名後的 Repo

@Service
public class SubCarService {

	@Autowired
	private SubCarRepository repo;

	// 取得所有訂閱車款
	public List<SubCar> getAllCars() {
		return repo.findAll();
	}

	// 依 ID 取得單一車款 (前端選車時會用到)
	public SubCar getCarById(Integer id) {
		return repo.findById(id).orElse(null);
	}
}