package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.BranchService;

@RestController
public class BranchRestController {
	
	@Autowired
	BranchService branchSer;

	@GetMapping("/allbranch")
	public List<String> branchName(){
		return branchSer.branchName();
	}
}
