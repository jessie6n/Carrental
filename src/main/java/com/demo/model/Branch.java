package com.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * The persistent class for the branch database table.
 * 
 */
@Data
@Entity
@Table(name = "branch")
@NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b")
public class Branch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String address;

	@Column(name = "branch_id")
	private String branchId;

	private String city;

	@Column(name = "map_thumbnail_url")
	private String mapThumbnailUrl;

	@Column(name = "name_en")
	private String nameEn;

	@Column(name = "name_zh")
	private String nameZh;

	@Column(name = "opening_hours")
	private String openingHours;

	@Column(name = "phone_number")
	private String phoneNumber;

	private String region;

}