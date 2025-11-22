package com.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "branch")
@Data
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "branch_id")
    private String branchId;

    @Column(name = "name_zh")
    private String nameZh;

    @Column(name = "name_en")
    private String nameEn;

    private String region;
    private String city;
    private String address;
    private String phone;
}
