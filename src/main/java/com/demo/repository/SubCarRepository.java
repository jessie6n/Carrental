package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.SubCar; // 引用改名後的 Model

@Repository
public interface SubCarRepository extends JpaRepository<SubCar, Integer> {
	// 如果未來有根據品牌或地區篩選的需求，可以在這裡加方法
	// List<SubCar> findByBrand(String brand);
	// List<SubCar> findByArea(String area);
}