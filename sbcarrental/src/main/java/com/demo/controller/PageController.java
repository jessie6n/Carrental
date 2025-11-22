package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.model.Branch;
import com.demo.service.BranchService;



@Controller
public class PageController {
	@Autowired
	BranchService branchService;
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/reserve")
    public String showReservePage(Model model) {

        // 從資料庫抓全部分店
        List<Branch> branches =branchService.findAll();

        model.addAttribute("branches", branches);

        return "reserve";
    }
    
}

