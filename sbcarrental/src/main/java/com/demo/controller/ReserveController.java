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

import com.demo.model.Car;
import com.demo.model.Order;
import com.demo.repository.OrderRepository;
import com.demo.service.CarService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReserveController {

    @Autowired
    CarService carService;

    @Autowired
    OrderRepository orderRepo;

    /* ============================================================
       STEP 1 → 接收資料、存 Session
    ============================================================ */
    @PostMapping("/reserve/step1")
    public String submitStep1(
            @RequestParam String pickupLocation,
            @RequestParam String returnLocation,
            @RequestParam String dateRange,
            @RequestParam String pickupTime,
            @RequestParam String returnTime,
            @RequestParam String carType,
            HttpSession session) {

        session.setAttribute("pickupLocation", pickupLocation);
        session.setAttribute("returnLocation", returnLocation);
        session.setAttribute("dateRange", dateRange);
        session.setAttribute("pickupTime", pickupTime);
        session.setAttribute("returnTime", returnTime);
        session.setAttribute("carType", carType);

        return "redirect:/reserve-step2";
    }

    /* ============================================================
       STEP 2 頁面顯示
    ============================================================ */
    @GetMapping("/reserve-step2")
    public String reserveStep2(Model model, HttpSession session) {

        String carType = (String) session.getAttribute("carType");
        List<Car> cars = new ArrayList<>();

        if ("五人座".equals(carType)) {
            cars = carService.findBySeats(5);
        } else if ("七九人座".equals(carType)) {
            cars = carService.findBySeatsIn(Arrays.asList(7, 8));
        }

        model.addAttribute("cars", cars);

        // 計算天數
        int rentalDays = 1;
        try {
            String[] range = ((String) session.getAttribute("dateRange")).split(" - ");
            LocalDate start = LocalDate.parse(range[0]);
            LocalDate end = LocalDate.parse(range[1]);
            rentalDays = (int) ChronoUnit.DAYS.between(start, end);
            if (rentalDays <= 0) rentalDays = 1;
        } catch (Exception e) {}

        model.addAttribute("rentalDays", rentalDays);
        return "reserve-step2";
    }

    /* ============================================================
       STEP 2 送出 → 驗證是否有選車
    ============================================================ */
    @PostMapping("/reserve/step2/submit")
    public String submitStep2(
            @RequestParam(required = false) Integer carId,
            @RequestParam(required = false) Boolean insurance,
            @RequestParam(required = false, defaultValue = "0") Integer childSeatQty,
            Model model,
            HttpSession session) {

        if (carId == null) {
            model.addAttribute("errorMsg", "請選擇要租的車輛");

            // 車款重新載入
            String carType = (String) session.getAttribute("carType");
            List<Car> cars = "五人座".equals(carType)
                    ? carService.findBySeats(5)
                    : carService.findBySeatsIn(Arrays.asList(7, 8));

            model.addAttribute("cars", cars);

            // 天數重新載入
            int rentalDays = 1;
            try {
                String[] parts = ((String) session.getAttribute("dateRange")).split(" - ");
                rentalDays = (int) ChronoUnit.DAYS.between(
                        LocalDate.parse(parts[0]),
                        LocalDate.parse(parts[1]));
            } catch (Exception e) {}

            model.addAttribute("rentalDays", rentalDays);
            return "reserve-step2";
        }

        session.setAttribute("carId", carId);
        session.setAttribute("insurance", insurance != null && insurance);
        session.setAttribute("childSeatQty", childSeatQty);

        return "redirect:/reserve/step3";
    }

    /* ============================================================
       STEP 3 → 訂單確認頁
    ============================================================ */
    @GetMapping("/reserve/step3")
    public String showStep3(HttpSession session, Model model) {

        // Step1 資料
        String dateRange = (String) session.getAttribute("dateRange");
        String[] parts = dateRange.split(" - ");
        String pickupDate = parts[0];
        String returnDate = parts[1];

        model.addAttribute("pickupDate", pickupDate);
        model.addAttribute("returnDate", returnDate);
        model.addAttribute("pickupLocation", session.getAttribute("pickupLocation"));
        model.addAttribute("returnLocation", session.getAttribute("returnLocation"));
        model.addAttribute("pickupTime", session.getAttribute("pickupTime"));
        model.addAttribute("returnTime", session.getAttribute("returnTime"));

        // Step2
        Integer carId = (Integer) session.getAttribute("carId");
        Car selectedCar = carService.findById(carId);
        session.setAttribute("selectedCar", selectedCar);
        model.addAttribute("selectedCar", selectedCar);

        Boolean insurance = (Boolean) session.getAttribute("insurance");
        Integer childSeatQty = (Integer) session.getAttribute("childSeatQty");
        model.addAttribute("insurance", insurance);
        model.addAttribute("childSeatQty", childSeatQty);

        // 天數
        long rentalDays = ChronoUnit.DAYS.between(
                LocalDate.parse(pickupDate),
                LocalDate.parse(returnDate));
        if (rentalDays <= 0) rentalDays = 1;
        session.setAttribute("rentalDays", rentalDays);
        model.addAttribute("rentalDays", rentalDays);

        // 計算金額
        int carTotal = selectedCar.getPrice() * (int) rentalDays;
        int seatCost = childSeatQty * 200;
        int insCost = insurance ? 1200 : 0;
        int totalAmount = carTotal + seatCost + insCost;

        session.setAttribute("totalAmount", totalAmount);
        model.addAttribute("totalAmount", totalAmount);

        return "reserve-step3";
    }

    /* ============================================================
       STEP 3 送出訂單 → 寫入 DB
    ============================================================ */
    @PostMapping("/reserve/step3/submit")
    public String submitOrder(@RequestParam Integer carId, HttpSession session) {

        Car car = carService.findById(carId);

        Order order = new Order();
        order.setOrderNo("OD" + System.currentTimeMillis());

        /* ★★★ 重新解析 dateRange（永遠最新） ★★★ */
        String dateRange = (String) session.getAttribute("dateRange");
        String[] parts = dateRange.split(" - ");
        order.setPickupDate(parts[0]);
        order.setReturnDate(parts[1]);

        // 其他維持不變
        order.setPickupLocation((String) session.getAttribute("pickupLocation"));
        order.setReturnLocation((String) session.getAttribute("returnLocation"));
        order.setPickupTime((String) session.getAttribute("pickupTime"));
        order.setReturnTime((String) session.getAttribute("returnTime"));

        // 車款
        order.setCarId(car.getId());
        order.setCarName(car.getName());
        order.setCarPrice(car.getPrice());

        // 天數
        order.setRentalDays(((Long) session.getAttribute("rentalDays")).intValue());

        // 加購
        order.setInsurance((Boolean) session.getAttribute("insurance"));
        order.setChildSeatQty((Integer) session.getAttribute("childSeatQty"));

        // 金額
        order.setTotalAmount((Integer) session.getAttribute("totalAmount"));

        order.setStatus("預約中");

        Order savedOrder = orderRepo.save(order);
        session.setAttribute("latestOrder", savedOrder);

        return "redirect:/reserve/step4";
    }

    /* ============================================================
       STEP 4 → 訂單完成頁
    ============================================================ */
    @GetMapping("/reserve/step4")
    public String showStep4(HttpSession session, Model model) {

        Order order = (Order) session.getAttribute("latestOrder");

        if(order == null){
            return "redirect:/reserve";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderId", order.getOrderNo());
        model.addAttribute("pickupLocation", order.getPickupLocation());
        model.addAttribute("returnLocation", order.getReturnLocation());
        model.addAttribute("pickupDate", order.getPickupDate());
        model.addAttribute("returnDate", order.getReturnDate());
        model.addAttribute("pickupTime", order.getPickupTime());
        model.addAttribute("returnTime", order.getReturnTime());

        model.addAttribute("selectedCar", order);  // 你要顯示車名、租金，就從 order 拿
        model.addAttribute("rentalDays", order.getRentalDays());
        model.addAttribute("insurance", order.getInsurance());
        model.addAttribute("childSeatQty", order.getChildSeatQty());
        model.addAttribute("totalAmount", order.getTotalAmount());

        return "reserve-step4";
    }
    
    
    

}
