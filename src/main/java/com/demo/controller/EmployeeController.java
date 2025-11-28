package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	
	//員工登入
	@PostMapping("/emplogin")
	public ResponseEntity<?> emplogin(@RequestParam String empId,@RequestParam String empPw){

		if(empId.equals("admin") && empPw.equals("1234")) {
	        Map<String, String> result = new HashMap<>();
	        result.put("empId", empId);
	        return ResponseEntity.ok(result); // 回傳 JSON
	    } else {
	        return ResponseEntity.status(401).body("帳號或密碼錯誤");
	    }
		
	}

}
