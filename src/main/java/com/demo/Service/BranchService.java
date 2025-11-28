package com.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Branch;
import com.demo.repository.BranchDao;

@Service
public class BranchService {
	@Autowired
	BranchDao repo;

	public List<Branch> getBranchesByRegion(String region) {
		if (region == null || region.trim().isEmpty() || region.equals("All")) {
			return repo.findAll();
		}
		return repo.findByRegion(region);
	}

	public List<Branch> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	// 這是 Admin 介面 (ordermanagement.html) 下拉選單需要的資料
	public List<String> branchName() {
		List<String> branchNameList = new ArrayList<>();
		for (Branch l : findAll()) {
			// 假設您資料庫存的是 nameZh (Branch model 屬性)
			branchNameList.add(l.getNameZh());
		}
		return branchNameList;
	}
}
