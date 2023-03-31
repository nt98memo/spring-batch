package com.example.batch.job1.chunk1.step1;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.lang.Nullable;

public class Step1Listener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
	}

	@Override
	@Nullable
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
