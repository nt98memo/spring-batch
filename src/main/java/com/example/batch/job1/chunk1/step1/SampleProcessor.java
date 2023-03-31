package com.example.batch.job1.chunk1.step1;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.common.entity.demo.Sample;

@Component
@StepScope
public class SampleProcessor implements ItemProcessor<Sample, Sample> {

	@Override
	public Sample process(Sample sample) throws Exception {
		return sample;
	}

}
