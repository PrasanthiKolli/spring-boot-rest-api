package com.example.springbatch.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob {
	
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	
	public Job firstJob() {
		
		return new JobBuilder("firstJob", jobRepository).start(firstStep()).build();	
	}


	private Step firstStep() {
		// TODO Auto-generated method stub
		return null;
	}

}
