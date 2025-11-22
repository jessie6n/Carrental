package com.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;


/**
 * The persistent class for the ordermember_view database table.
 * 
 */
@Entity
@Table(name="ordermember_view")
@NamedQuery(name="OrdermemberView.findAll", query="SELECT o FROM OrdermemberView o")
public class OrdermemberView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="car_id")
	private int carId;

	@Column(name="car_name")
	private String carName;

	@Column(name="car_price")
	private int carPrice;

	@Column(name="child_seat_qty")
	private int childSeatQty;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Id
	private int id;

	private Integer insurance;

	@Column(name="member_email")
	private String memberEmail;

	@Column(name="member_idnumber")
	private String memberIdnumber;

	@Column(name="member_name")
	private String memberName;

	@Column(name="member_no")
	private String memberNo;

	@Column(name="member_phone")
	private String memberPhone;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="pickup_date")
	private String pickupDate;

	@Column(name="pickup_location")
	private String pickupLocation;

	@Column(name="pickup_time")
	private String pickupTime;

	@Column(name="rental_days")
	private int rentalDays;

	@Column(name="return_date")
	private String returnDate;

	@Column(name="return_location")
	private String returnLocation;

	@Column(name="return_time")
	private String returnTime;

	private String status;

	@Column(name="total_amount")
	private int totalAmount;

	public OrdermemberView() {
	}

	public int getCarId() {
		return this.carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getCarName() {
		return this.carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getCarPrice() {
		return this.carPrice;
	}

	public void setCarPrice(int carPrice) {
		this.carPrice = carPrice;
	}

	public int getChildSeatQty() {
		return this.childSeatQty;
	}

	public void setChildSeatQty(int childSeatQty) {
		this.childSeatQty = childSeatQty;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getInsurance() {
		return this.insurance;
	}

	public void setInsurance(Integer insurance) {
		this.insurance = insurance;
	}

	public String getMemberEmail() {
		return this.memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberIdnumber() {
		return this.memberIdnumber;
	}

	public void setMemberIdnumber(String memberIdnumber) {
		this.memberIdnumber = memberIdnumber;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNo() {
		return this.memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPickupDate() {
		return this.pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getPickupLocation() {
		return this.pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public String getPickupTime() {
		return this.pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public int getRentalDays() {
		return this.rentalDays;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	public String getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnLocation() {
		return this.returnLocation;
	}

	public void setReturnLocation(String returnLocation) {
		this.returnLocation = returnLocation;
	}

	public String getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

}