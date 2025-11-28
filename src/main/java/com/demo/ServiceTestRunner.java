package com.demo; // ⚠️請修改為您的 package

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.Service.BranchService;
import com.demo.Service.CarService;

@Component
public class ServiceTestRunner implements CommandLineRunner {

	@Autowired
	CarService carService;
	@Autowired
	BranchService branchService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=================================");
		System.out.println("🧪 Service 層測試開始...");

		try {
			// 嘗試呼叫 Service 方法，如果沒報錯 NullPointerException，代表注入成功
			var cars = carService.getAllCars();
			System.out.println("✅ CarService 呼叫成功！目前資料庫車輛數：" + cars.size());

			// 測試特殊查詢
			var smallCars = carService.findBySeats(5);
			System.out.println("✅ findBySeats(5) 呼叫成功！");
			// 測試 BranchService
			var branches = branchService.findAll();
			var names = branchService.branchName();
			System.out.println("✅ BranchService: 找到 " + branches.size() + " 間分店");
			System.out.println("   分店名單: " + names);

		} catch (Exception e) {
			System.err.println("❌ Service 測試失敗：" + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("=================================");
	}
}