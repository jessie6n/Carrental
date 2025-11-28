package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.ADSCar;

public interface ADSCarRepository extends JpaRepository<ADSCar, Long> {
}
