package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
	
	List<UserDetails> findByRole(String role);

}
