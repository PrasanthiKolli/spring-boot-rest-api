package com.example.springboot.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.springboot.model.UserDetails;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailsRepository UserDetailsRepository;
	@Override
	public void run(String... args) throws Exception {
		UserDetailsRepository.save(new UserDetails("ranga","Admin"));
		UserDetailsRepository.save(new UserDetails("ravi","Admin"));
		UserDetailsRepository.save(new UserDetails("jhon","Admin"));
		
		List<UserDetails> users= UserDetailsRepository.findAll();
		
		users.forEach(user ->logger.info( user.toString()));
	}
	
	

}
