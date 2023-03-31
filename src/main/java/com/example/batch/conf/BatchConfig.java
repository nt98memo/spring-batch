package com.example.batch.conf;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batch.job1.Job1Listener;
import com.example.batch.job1.chunk1.Job1ChunkListener;
import com.example.batch.job1.chunk1.step1.SampleProcessor;
import com.example.batch.job1.chunk1.step1.SampleReader;
import com.example.batch.job1.chunk1.step1.SampleWritter;
import com.example.common.entity.demo.Sample;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
public class BatchConfig {

	@Autowired
	private SampleReader reader;
	@Autowired
	private SampleProcessor processor;
	@Autowired
	private SampleWritter writer;

	@Autowired
	private Job1Listener job1Listener;
	@Autowired
	private Job1ChunkListener job1ChunkListener;


	@Bean("batchDbProperties")
	@ConfigurationProperties("batch.jdbc")
	public DataSourceProperties batchDbProperties() {
	  return new DataSourceProperties();
	}

	@Bean(name = "batchDatasource")
	public DataSource batchDatasource(@Qualifier("batchDbProperties") DataSourceProperties batchDbProperties) {
		return batchDbProperties.initializeDataSourceBuilder().build();
	}

	@Bean(name = "batchTransactionManager")
	protected PlatformTransactionManager batchTransactionManager(@Qualifier("batchDatasource") DataSource batchDatasource) {
		return new DataSourceTransactionManager(batchDatasource);
	}

	@Bean
	public Step step1(JobRepository jobRepository, @Qualifier("demoTransactionManager") PlatformTransactionManager transactionManager) {
		return new StepBuilder("sampleStep", jobRepository)
					.<Sample, Sample>chunk(10, transactionManager)
					.reader(reader)
					.processor(processor)
					.writer(writer)
					.listener(job1ChunkListener)
					.build();
	}
	
    @Bean
    public Job myJob(JobRepository jobRepository, @Qualifier("demoTransactionManager") PlatformTransactionManager transactionManager) {
	return new JobBuilder("sampleJob", jobRepository)
			    .listener(job1Listener)
				.start(step1(jobRepository, transactionManager))
				.next(step1(jobRepository, transactionManager))
				.next(step1(jobRepository, transactionManager))
				.build();
    }
    
}