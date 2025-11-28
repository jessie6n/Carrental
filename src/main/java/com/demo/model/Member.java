package com.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the member database table.
 * 
 */
@Entity
@NamedQuery(name="Member.findAll", query="SELECT m FROM Member m")
@EntityListeners(AuditingEntityListener.class) 
@Data
@NoArgsConstructor
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String email;

	private String gender;

	@Column(name="id_number")
	private String idNumber;

	@Column(name="member_id")
	private String memberId;

	private String name;

	private String password;

	private String phone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_date")
	@CreatedDate()
	private Date registerDate;
	
	// 以下為重設密碼需要的欄位
    private String resetToken;       // 存放 hashed token
    private LocalDateTime tokenExpire; // token 過期時間
}