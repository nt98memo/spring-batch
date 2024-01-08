package com.example.batch.job1.step2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.entity.demo.Sample;
import com.example.common.entity.demo.SampleParent;
import com.example.common.enumeration.Flg;
import com.example.common.service.demo.SampleService;

@Component
@StepScope
public class SampleTasklet implements Tasklet, StepExecutionListener {

	@Autowired
	private SampleService sampleService;

	@Override
	public void beforeStep(StepExecution stepExecution) {
    	System.out.println("----------SampleTasklet beforeStep----------");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
    	System.out.println("----------SampleTasklet afterStep----------");
    	return stepExecution.getExitStatus();
	}
	
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RepeatStatus execute(StepContribution contribution, ChunkContext context) {
    	System.out.println("----------SampleTasklet execute----------");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("com/example/batch/sample.tsv").getInputStream()))) {

        	String line = null;
        	while((line = reader.readLine()) != null) {

                String[] lineSplit = line.split("\t");
                Sample sample = new Sample();
                sample.setIntCol(Integer.parseInt(lineSplit[0]));
                sample.setLongCol(Long.parseLong(lineSplit[1]));
                sample.setDecimalCol(BigDecimal.valueOf(Double.parseDouble(lineSplit[2])));
                sample.setTextCol(lineSplit[3]);
                sample.setPasswordCol(lineSplit[4]);
                sample.setTextareaCol(lineSplit[5]);
                sample.setRadioCol(Flg.OFF);
                sample.setSelectCol(Flg.OFF);
                sample.setDateCol(new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(lineSplit[6]).getTime()));
                sample.setTimeCol(Time.valueOf(lineSplit[7]));
                sample.setDatetimeCol(new Timestamp((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")).parse(lineSplit[8]).getTime()));
                SampleParent sampleParent = new SampleParent();
                sampleParent.setId(Long.parseLong(lineSplit[9]));
                sample.setSampleParent(sampleParent);
                
                sampleService.insert(sample);
                
        	}
        	
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
        
        contribution.setExitStatus(new ExitStatus("success"));
        return RepeatStatus.FINISHED;
    }
}
