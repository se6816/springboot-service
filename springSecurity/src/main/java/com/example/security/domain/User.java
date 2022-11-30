package com.example.security.domain;



import java.security.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	private String provider;
	private String providerId;
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@Builder
	public User(String username, String password, String email, String role, String provider, String providerId) {
		this.username=username;
		this.password=password;
		this.email=email;
		this.role=role;
		this.provider=provider;
		this.providerId=providerId;
	}
}
