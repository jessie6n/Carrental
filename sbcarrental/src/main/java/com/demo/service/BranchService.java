package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.model.Branch;
import com.demo.repository.BranchRepository;

@Service
public class BranchService {

    @Autowired
    BranchRepository branchRepo;

    public List<Branch> findAll() {
        return branchRepo.findAll();
    }
    
    public List<String> branchName(){
    	List<String> branchNameList=new ArrayList();
    	for(Branch l:findAll()) {
    		branchNameList.add(l.getNameZh());
    	}
    	return branchNameList;
    }
}

