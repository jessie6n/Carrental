package com.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sub_cars") // ⭐ 修改表名，避免與 car 衝突
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubCar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String brand;

	private Integer price; // 月費價格

	private String area;

	private String store;

	private String img; // 例如 /images/subcar1.jpg
}