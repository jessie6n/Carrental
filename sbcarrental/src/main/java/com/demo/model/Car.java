package com.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "car")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int seats;
    private int doors;
    private int luggage;
    private int cc;
    private String fuel;
    private int price;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "mileage_fee")
 
    private Double mileageFee;
    
    
}
