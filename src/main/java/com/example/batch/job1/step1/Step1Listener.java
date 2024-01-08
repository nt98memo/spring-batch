package com.example.batch.job1.step1;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Step1Listener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
    	System.out.println("----------Step1Listener beforeStep----------");
	}

	@Override
	@Nullable
	public ExitStatus afterStep(StepExecution stepExecution) {
    	System.out.println("----------Step1Listener afterStep----------");
    	return stepExecution.getExitStatus();
	}
}
