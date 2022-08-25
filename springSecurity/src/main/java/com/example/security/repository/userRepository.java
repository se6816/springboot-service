package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.domain.User;

public interface userRepository extends JpaRepository<User,Integer>{

	//findBy 규칙  
	// select * from User where username=?
	public User findByUsername(String username);
}
