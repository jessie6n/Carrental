package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Member;
import com.demo.model.Order;
import com.demo.model.OrdermemberView;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	Order findByOrderNo(String orderNo);
	
	List<Order> findByMemberId(@Param("member_no") String memberId);
	
	@Query(value = "SELECT * FROM ordermember_view "
			+ "WHERE (:member_no IS NULL OR member_no LIKE CONCAT('%', :member_no, '%'))\r\n"
			+ "  AND (:member_name IS NULL OR member_name = :member_name)\r\n"
			+ "  AND (:member_idnumber IS NULL OR member_idnumber = :member_idnumber)\r\n"
			+ "  AND (:member_phone IS NULL OR member_phone = :member_phone)\r\n"
			+ "  AND (:pickup_location IS NULL OR pickup_location = :pickup_location)\r\n"
			+ "  AND (:pickup_date_start IS NULL OR pickup_date >= :pickup_date_start) "
	        + "  AND (:pickup_date_end IS NULL OR pickup_date <= :pickup_date_end) "
			+ "  AND (:status IS NULL OR status = :status);", nativeQuery = true)
		List<OrdermemberView> searchOrder(
				@Param("member_no") String member_no,
		        @Param("member_name") String member_name,
		        @Param("member_idnumber") String member_idnumber,
		        @Param("member_phone") String member_phone,
				@Param("pickup_location") String pickup_location,
		        @Param("pickup_date_start") String pickup_date_start,
		        @Param("pickup_date_end") String pickup_date_end,
		        @Param("status") String status);
		
}

