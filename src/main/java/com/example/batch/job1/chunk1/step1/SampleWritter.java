package com.example.batch.job1.chunk1.step1;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.entity.demo.Sample;
import com.example.common.service.demo.SampleService;

@Component
@StepScope
public class SampleWritter implements ItemWriter<Sample> {

	@Autowired
	private SampleService sampleService;

	@Override
	public void write(Chunk<? extends Sample> chunk) throws Exception {
		
		for (Sample entity : chunk) {
			sampleService.insert(entity);
		}
		
	}

}
