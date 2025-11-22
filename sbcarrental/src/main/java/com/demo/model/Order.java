package com.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderNo;
    
    @Column(name = "member_no")
    private String memberId;

    private String pickupLocation;
    private String returnLocation;

    private String pickupDate;
    private String returnDate;

    private String pickupTime;
    private String returnTime;

    private Integer carId;
    private String carName;
    private Integer carPrice;

    private Integer rentalDays;

    private Boolean insurance;
    private Integer childSeatQty;

    private Integer totalAmount;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Order() {
        this.createdAt = LocalDateTime.now();
    }
}
