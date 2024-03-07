package com.example.springbatch.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.springbatch.service.SecondTasklet;

@Configuration
public class sampleTask {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	@Autowired
	private SecondTasklet secondTasklet;
	@Bean
	public Job firstJob() {

		return new JobBuilder("firstJob", jobRepository)
				.start(firstStep())
				.next(secondStep())
				.build();

	}

	public Step firstStep() {

		return new StepBuilder("firstStep", jobRepository)
				.tasklet(firstTask(), platformTransactionManager)
				.build();
	}

	public Tasklet firstTask() {

		return new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("This is first tasklet step");
				return RepeatStatus.FINISHED;
			}

		};
	}
	
	public Step secondStep() {

		return new StepBuilder("SecondStep", jobRepository)
				.tasklet(secondTasklet, platformTransactionManager)
				.build();
	}
}
