package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Branch;
@Repository
public interface BranchDao extends JpaRepository<Branch, Integer> {
	public List<Branch> findByRegion(String region);
}
