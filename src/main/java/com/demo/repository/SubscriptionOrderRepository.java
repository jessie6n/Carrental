package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // 建議加上註解

import com.demo.model.SubscriptionOrder;

@Repository
public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, Integer> {

	// 查詢某會員的所有訂閱單
	List<SubscriptionOrder> findByMemberId(String memberId);

	// 檢查該會員是否已訂閱過這台車 (可依業務需求決定是否使用)
	// 注意：這裡的 CarId 指的是 SubCar 的 ID
	boolean existsByMemberIdAndCarId(String memberId, Integer carId);

	// 透過訂單編號查詢 (建議補上，Controller 可能會用到)
	SubscriptionOrder findByOrderNo(String orderNo);
}