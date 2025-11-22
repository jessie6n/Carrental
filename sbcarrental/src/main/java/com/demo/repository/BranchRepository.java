package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

}

