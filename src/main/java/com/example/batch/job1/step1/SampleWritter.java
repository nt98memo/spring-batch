package com.example.batch.job1.step1;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.entity.demo.Sample;
import com.example.common.service.demo.SampleService;

@Component
@StepScope
public class SampleWritter implements ItemWriter<Sample> {
	
	@Autowired
	private SampleService sampleService;

	@Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
	public void write(Chunk<? extends Sample> chunk) throws Exception {
    	System.out.println("----------SampleWritter write----------");

    	for (Sample entity : chunk) {
			sampleService.insert(entity);
		}
        
	}

}
