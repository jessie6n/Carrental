package com.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.SubscriptionOrder;
import com.demo.repository.SubscriptionOrderRepository;

@Service
public class SubscriptionOrderService {

	@Autowired
	private SubscriptionOrderRepository repo;

	// C: 建立訂單
	public SubscriptionOrder createOrder(SubscriptionOrder order) {
		// 這裡可以加入產生 orderNo 的邏輯，如果 Controller 沒做的話
		if (order.getOrderNo() == null) {
			order.setOrderNo("SUB" + System.currentTimeMillis());
		}
		// 預設狀態
		if (order.getStatus() == null) {
			order.setStatus("申請中");
		}
		return repo.save(order);
	}

	// R: 查詢所有訂單 (管理員用)
	public List<SubscriptionOrder> getAllOrders() {
		return repo.findAll();
	}

	// R: 查詢單筆訂單
	public SubscriptionOrder getOrderById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("訂單不存在 id=" + id));
	}

	// R: 依會員 ID 查詢 (會員中心用) - ⭐ 新增
	public List<SubscriptionOrder> getOrdersByMemberId(String memberId) {
		return repo.findByMemberId(memberId);
	}

	// U: 更新訂單
	public SubscriptionOrder updateOrder(Integer id, SubscriptionOrder newData) {
		SubscriptionOrder order = getOrderById(id);

		order.setStore(newData.getStore());
		order.setStartDate(newData.getStartDate());
		order.setStartTime(newData.getStartTime());
		order.setMonths(newData.getMonths());
		order.setMileageBonus(newData.getMileageBonus());
		order.setTotalPrice(newData.getTotalPrice());
		order.setFinalPrice(newData.getFinalPrice());

		// 如果有狀態更新
		if (newData.getStatus() != null) {
			order.setStatus(newData.getStatus());
		}

		return repo.save(order);
	}

	// D: 刪除訂單
	public void deleteOrder(Integer id) {
		repo.deleteById(id);
	}
}