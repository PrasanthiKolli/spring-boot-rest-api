package com.example.springbatch.listner;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class productListner implements JobExecutionListener{
	
	public void beforeJob(JobExecution je) {
		
		System.out.println("start date : "+ new Date());
		System.out.println("job status : "+ je.getStatus());
	}

	public void afterJob(JobExecution je) {
		System.out.println("end date : "+ new Date());
		System.out.println("job status : "+ je.getStatus());
	}

}
