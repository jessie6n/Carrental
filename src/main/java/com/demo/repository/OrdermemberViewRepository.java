package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.OrdermemberView;

public interface OrdermemberViewRepository extends JpaRepository<OrdermemberView, Integer> {
	public OrdermemberView findByOrderNo(String odNo);

}
