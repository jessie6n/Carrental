package com.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Car;
import com.demo.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	CarRepository repo;

	public List<Car> getAllCars() {
		return repo.findAll();
	}

	public Car findById(Integer id) {

		return repo.findById(id).orElse(null);
	}

	public List<Car> findBySeats(int seats) {

		return repo.findBySeats(seats);
	}

	public List<Car> findBySeatsIn(List<Integer> seatsList) {

		return repo.findBySeatsIn(seatsList);
	}
}
