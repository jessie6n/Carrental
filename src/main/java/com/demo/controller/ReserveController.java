package com.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.Service.CarService;
import com.demo.model.Car;
import com.demo.model.Order;
import com.demo.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReserveController {

	@Autowired
	CarService carService;

	@Autowired
	OrderRepository orderRepo;

	/*
	 * ============================================================ STEP 1 Submit →
	 * 接收資料、存 Session ============================================================
	 */
	@PostMapping("/reserve/step1")
	public String submitStep1(@RequestParam String pickupLocation, @RequestParam String returnLocation,
			@RequestParam String dateRange, @RequestParam String pickupTime, @RequestParam String returnTime,
			@RequestParam String carType, HttpSession session) {

		session.setAttribute("pickupLocation", pickupLocation);
		session.setAttribute("returnLocation", returnLocation);
		session.setAttribute("dateRange", dateRange);
		session.setAttribute("pickupTime", pickupTime);
		session.setAttribute("returnTime", returnTime);
		session.setAttribute("carType", carType);

		return "redirect:/reserve-step2";
	}

	/*
	 * ============================================================ STEP 2 Show →
	 * 顯示符合條件的車輛 ============================================================
	 */
	@GetMapping("/reserve-step2")
	public String reserveStep2(Model model, HttpSession session) {

		String carType = (String) session.getAttribute("carType");
		// 防呆：如果直接輸入網址進來，沒有 session 資料，踢回首頁
		if (carType == null) {
			return "redirect:/reserve";
		}

		List<Car> cars = new ArrayList<>();
		if ("五人座".equals(carType)) {
			cars = carService.findBySeats(5);
		} else if ("七九人座".equals(carType)) {
			cars = carService.findBySeatsIn(Arrays.asList(7, 8));
		}

		model.addAttribute("cars", cars);

		// 計算租借天數
		int rentalDays = 1;
		try {
			String[] range = ((String) session.getAttribute("dateRange")).split(" - ");
			LocalDate start = LocalDate.parse(range[0]);
			LocalDate end = LocalDate.parse(range[1]);
			rentalDays = (int) ChronoUnit.DAYS.between(start, end);
			if (rentalDays <= 0)
				rentalDays = 1;
		} catch (Exception e) {
			// 解析失敗時預設為 1 天
		}

		model.addAttribute("rentalDays", rentalDays);
		return "reserve-step2";
	}

	/*
	 * ============================================================ STEP 2 Submit →
	 * 選擇車輛與加購項目 ============================================================
	 */
	@PostMapping("/reserve/step2/submit")
	public String submitStep2(@RequestParam(required = false) Integer carId,
			@RequestParam(required = false) Boolean insurance,
			@RequestParam(required = false, defaultValue = "0") Integer childSeatQty, Model model,
			HttpSession session) {

		if (carId == null) {
			model.addAttribute("errorMsg", "請選擇要租的車輛");

			// 發生錯誤，需重新載入頁面所需資料 (車輛列表)
			return reserveStep2(model, session);
		}

		session.setAttribute("carId", carId);
		session.setAttribute("insurance", insurance != null && insurance);
		session.setAttribute("childSeatQty", childSeatQty);

		return "redirect:/reserve/step3";
	}

	/*
	 * ============================================================ STEP 3 Show →
	 * 訂單確認頁 ============================================================
	 */
	@GetMapping("/reserve/step3")
	public String showStep3(HttpSession session, Model model) {

		String dateRange = (String) session.getAttribute("dateRange");
		if (dateRange == null)
			return "redirect:/reserve";

		String[] parts = dateRange.split(" - ");
		String pickupDate = parts[0];
		String returnDate = parts[1];

		model.addAttribute("pickupDate", pickupDate);
		model.addAttribute("returnDate", returnDate);
		model.addAttribute("pickupLocation", session.getAttribute("pickupLocation"));
		model.addAttribute("returnLocation", session.getAttribute("returnLocation"));
		model.addAttribute("pickupTime", session.getAttribute("pickupTime"));
		model.addAttribute("returnTime", session.getAttribute("returnTime"));

		// 車輛資訊
		Integer carId = (Integer) session.getAttribute("carId");
		Car selectedCar = carService.findById(carId);
		model.addAttribute("selectedCar", selectedCar);

		// 加購資訊
		Boolean insurance = (Boolean) session.getAttribute("insurance");
		Integer childSeatQty = (Integer) session.getAttribute("childSeatQty");
		model.addAttribute("insurance", insurance);
		model.addAttribute("childSeatQty", childSeatQty);

		// 天數與金額計算
		long rentalDays = ChronoUnit.DAYS.between(LocalDate.parse(pickupDate), LocalDate.parse(returnDate));
		if (rentalDays <= 0)
			rentalDays = 1;

		model.addAttribute("rentalDays", rentalDays);
		session.setAttribute("rentalDays", rentalDays); // 補存 Session 供下一步使用

		int carTotal = selectedCar.getPrice() * (int) rentalDays;
		int seatCost = childSeatQty * 200;
		int insCost = insurance ? 1200 : 0;
		int totalAmount = carTotal + seatCost + insCost;

		session.setAttribute("totalAmount", totalAmount);
		model.addAttribute("totalAmount", totalAmount);

		return "reserve-step3";
	}

	/*
	 * ============================================================ STEP 3 Submit →
	 * 寫入資料庫 ============================================================
	 */
	@PostMapping("/reserve/step3/submit")
	public String submitOrder(@RequestParam Integer carId, HttpSession session) {

		Car car = carService.findById(carId);

		Order order = new Order();
		order.setOrderNo("OD" + System.currentTimeMillis());

		// 從 Session 取回資料
		String dateRange = (String) session.getAttribute("dateRange");
		String[] parts = dateRange.split(" - ");
		order.setPickupDate(parts[0]);
		order.setReturnDate(parts[1]);

		order.setPickupLocation((String) session.getAttribute("pickupLocation"));
		order.setReturnLocation((String) session.getAttribute("returnLocation"));
		order.setPickupTime((String) session.getAttribute("pickupTime"));
		order.setReturnTime((String) session.getAttribute("returnTime"));

		order.setCarId(car.getId());
		order.setCarName(car.getName());
		order.setCarPrice(car.getPrice());

		// 處理 Long 轉 Integer 的潛在問題
		Object daysObj = session.getAttribute("rentalDays");
		int days = (daysObj instanceof Long) ? ((Long) daysObj).intValue() : (Integer) daysObj;
		order.setRentalDays(days);

		order.setInsurance((Boolean) session.getAttribute("insurance"));
		order.setChildSeatQty((Integer) session.getAttribute("childSeatQty"));

		order.setTotalAmount((Integer) session.getAttribute("totalAmount"));

		order.setStatus("預約中");

		// ⭐ 新增：從 Session 取得會員 ID 並寫入訂單
		String memberId = (String) session.getAttribute("loginUserId");
		if (memberId != null) {
			order.setMemberId(memberId); // 確保 Order.java 有此欄位
		} else {
			// 防呆：理論上不該發生，因為 PageController 擋過了
			// 但為了安全，如果 Session 過期了，可以導回首頁或報錯
			return "redirect:http://127.0.0.1:5500/index.html";
		}
		Order savedOrder = orderRepo.save(order);
		session.setAttribute("latestOrder", savedOrder);

		return "redirect:/reserve/step4";
	}

	/*
	 * ============================================================ STEP 4 Show →
	 * 訂單完成 ============================================================
	 */
	@GetMapping("/reserve/step4")
	public String showStep4(HttpSession session, Model model) {

		Order order = (Order) session.getAttribute("latestOrder");

		if (order == null) {
			return "redirect:/reserve";
		}
		model.addAttribute("order", order);

		return "reserve-step4";
	}
}