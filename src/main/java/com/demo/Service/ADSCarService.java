package com.demo.Service;

import java.util.List;

import com.demo.model.ADSCar;

public interface ADSCarService {
    List<ADSCar> findAll();
    ADSCar findById(Long id);
    ADSCar save(ADSCar car);
}
