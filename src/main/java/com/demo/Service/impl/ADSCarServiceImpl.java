package com.demo.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.Service.ADSCarService;
import com.demo.model.ADSCar;
import com.demo.repository.ADSCarRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ADSCarServiceImpl implements ADSCarService {

	private final ADSCarRepository adscarRepository;

	@Override
	public List<ADSCar> findAll() {
		return adscarRepository.findAll();
	}

	@Override
	public ADSCar findById(Long id) {
		return adscarRepository.findById(id).orElse(null);
	}

	@Override
	public ADSCar save(ADSCar car) {
		return adscarRepository.save(car);
	}
}
