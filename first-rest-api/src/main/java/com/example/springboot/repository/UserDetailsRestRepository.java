package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.springboot.model.UserDetails;


public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long>{
	
	List<UserDetails> findByRole(String role);

}
