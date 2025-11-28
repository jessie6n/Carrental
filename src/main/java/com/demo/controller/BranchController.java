package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Service.BranchService;
import com.demo.model.Branch;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BranchController {

	@Autowired
	BranchService service;

	@GetMapping("/branches")
	public ResponseEntity<List<Branch>> getBranchesByRegion(@RequestParam(required = false) String region) {
		List<Branch> data = service.getBranchesByRegion(region);

		return ResponseEntity.ok(data);

	}

	// 提供給 ordermanagement.html 下拉選單
	@GetMapping("/allbranch")
	public List<String> branchName() {
		return service.branchName();
	}
}
