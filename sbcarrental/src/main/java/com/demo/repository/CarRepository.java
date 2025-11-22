package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
	
	List<Car> findBySeats(Integer seats);

    List<Car> findBySeatsIn(List<Integer> seats);
}

