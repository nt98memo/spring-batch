package com.example.batch.job1;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class Job1Listener implements JobExecutionListener {

    @Override
	public void beforeJob(JobExecution jobExecution) {
    	System.out.println("Job1LIstener beforeJob");
	}

    @Override
    public void afterJob(JobExecution jobExecution) {
    	System.out.println("Job1LIstener afterJob");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
        }
    }
    
}
