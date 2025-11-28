package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

	// 配合 Service 的 findBySeats
	List<Car> findBySeats(int seats);

	// 配合 Service 的 findBySeatsIn (查詢 7人 或 9人座)
	List<Car> findBySeatsIn(List<Integer> seats);
}