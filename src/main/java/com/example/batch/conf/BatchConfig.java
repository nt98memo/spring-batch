package com.example.batch.conf;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batch.job1.Job1Listener;
import com.example.batch.job1.step1.Job1ChunkListener;
import com.example.batch.job1.step1.SampleProcessor;
import com.example.batch.job1.step1.SampleReader;
import com.example.batch.job1.step1.SampleWritter;
import com.example.batch.job1.step1.Step1Listener;
import com.example.batch.job1.step2.SampleTasklet;
import com.example.common.entity.demo.Sample;


@Configuration
public class BatchConfig {

	@Autowired
	private Job1Listener job1Listener;
	
	@Autowired
	private Job1ChunkListener job1ChunkListener;
	@Autowired
	private Step1Listener step1Listener;
	@Autowired
	private SampleReader reader;
	@Autowired
	private SampleProcessor processor;
	@Autowired
	private SampleWritter writer;

	@Autowired
	private SampleTasklet sampleTasklet;
	
	@Bean
	public JpaTransactionManager jpaTransactionManager() {
	    return new JpaTransactionManager();
	}
	
	@Bean("batchDbProperties")
	@ConfigurationProperties("batch.jdbc")
	public DataSourceProperties batchDbProperties() {
	    return new DataSourceProperties();
	}

	@Bean(name = "batchDataSource")
	@BatchDataSource
	public DataSource batchDataSource(@Qualifier("batchDbProperties") DataSourceProperties batchDbProperties) {
		return batchDbProperties.initializeDataSourceBuilder().build();
	}

	@Bean(name = "batchTransactionManager")
	protected PlatformTransactionManager batchTransactionManager(@Qualifier("batchDataSource") DataSource batchDataSource) {
		return new DataSourceTransactionManager(batchDataSource);
	}

	@Bean(name = "step1")
	public Step step1(JobRepository jobRepository, JpaTransactionManager transactionManager) {
		return new StepBuilder("sampleStep", jobRepository)
					.<Sample, Sample>chunk(2, transactionManager)
					.reader(reader)
					.processor(processor)
					.writer(writer)
					.listener(step1Listener)
					.listener(job1ChunkListener)
					.build();
	}
	
	@Bean(name = "step2")
	public Step step2(JobRepository jobRepository, JpaTransactionManager transactionManager) {
		return new StepBuilder("sampleTasklet", jobRepository)
					.tasklet(sampleTasklet, transactionManager)
					.build();
	}
	
    @Bean
    public Job myJob(JobRepository jobRepository, @Qualifier("demoTransactionManager") PlatformTransactionManager transactionManager, @Qualifier("step1") Step step1, @Qualifier("step2") Step step2) {
		return new JobBuilder("sampleJob", jobRepository)
				    .listener(job1Listener)
				    .incrementer(new RunIdIncrementer())
	                .start(step1)
					.next(step2)
	                .build();
    }
    
}