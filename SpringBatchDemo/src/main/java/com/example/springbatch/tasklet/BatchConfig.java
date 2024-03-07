package com.example.springbatch.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.springbatch.model.Product;
import com.example.springbatch.processor.ProductProcessor;

//@Configuration
public class BatchConfig {

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

	@Bean
	public ItemReader<Product> reader() {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("product.csv"));
		reader.setLineMapper(new DefaultLineMapper<Product>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter(DELIMITER_COMMA);
						setNames("prodId", "prodCode", "prodCost");
					}
				});

				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
					{
						setTargetType(Product.class);
					}
				});
			}
		});
		return reader;
	}
	
	@Bean
	public ItemProcessor<Product, Product> processor(){
		return new ProductProcessor(); 
	}
	
	@Bean
	public ItemWriter<Product> writer(){
		return null; 
	}

}
