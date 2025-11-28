package com.demo.model;

import java.sql.Timestamp; // 確保引入正確的時間型別

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "subscription_order")
public class SubscriptionOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "order_no", unique = true, nullable = false)
	private String orderNo;

	// 這裡存的是 Member 的 memberId (例如 m00005)
	@Column(name = "member_id", nullable = false)
	private String memberId;

	// 這裡存的是 SubCar 的 id
	@Column(name = "car_id", nullable = false) // ⭐ 建議改名讓語意更清楚
	private Integer carId;

	@Column(name = "status")
	private String status; // 例如: "申請中", "已付款"

	private String store;
	private String startDate;
	private String startTime;

	private Integer months; // 訂閱週期 (3, 6, 9, 12)
	private Integer mileageBonus; // 里程包

	private Integer totalPrice;
	private Integer finalPrice;

	// 自動寫入建立時間
	@Column(name = "created_at", insertable = false, updatable = false)
	@org.hibernate.annotations.CreationTimestamp // 使用 Hibernate 自動生成時間
	private Timestamp createdAt;
}