package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.Order;
import com.demo.model.OrdermemberView; // ⚠️ 確保引用了這個 View Model

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findByOrderNo(String orderNo);

	// 會員中心：查詢自己的歷史訂單
	List<Order> findByMemberId(String memberId);

	// 員工後台：多條件搜尋訂單 (注意：這裡回傳的是 OrdermemberView)
	// 這段 SQL 會查詢 ordermember_view 這個表格/視圖
	@Query(value = "SELECT * FROM ordermember_view "
			+ "WHERE (:member_no IS NULL OR member_no LIKE CONCAT('%', :member_no, '%')) "
			+ "  AND (:member_name IS NULL OR member_name = :member_name) "
			+ "  AND (:member_idnumber IS NULL OR member_idnumber = :member_idnumber) "
			+ "  AND (:member_phone IS NULL OR member_phone = :member_phone) "
			+ "  AND (:pickup_location IS NULL OR pickup_location = :pickup_location) "
			+ "  AND (:pickup_date_start IS NULL OR pickup_date >= :pickup_date_start) "
			+ "  AND (:pickup_date_end IS NULL OR pickup_date <= :pickup_date_end) "
			+ "  AND (:status IS NULL OR status = :status)", nativeQuery = true)
	List<OrdermemberView> searchOrder(@Param("member_no") String member_no, @Param("member_name") String member_name,
			@Param("member_idnumber") String member_idnumber, @Param("member_phone") String member_phone,
			@Param("pickup_location") String pickup_location, @Param("pickup_date_start") String pickup_date_start,
			@Param("pickup_date_end") String pickup_date_end, @Param("status") String status);
}