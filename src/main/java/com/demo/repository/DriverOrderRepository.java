package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.DriverOrder;

public interface DriverOrderRepository extends JpaRepository<DriverOrder, Long> {
	public List<DriverOrder> findByPhone(String phone);
	public Optional<DriverOrder> findByOrderNo(String orderNo);
	public List<DriverOrder> findByMemberId(String memberId);
}
