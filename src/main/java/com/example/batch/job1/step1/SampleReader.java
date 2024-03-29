package com.example.batch.job1.step1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.common.entity.demo.Sample;
import com.example.common.entity.demo.SampleParent;
import com.example.common.enumeration.Flg;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@StepScope
public class SampleReader implements ItemReader<Sample>{

	private BufferedReader reader;
	
    @PostConstruct
    private void postConstruct() {
    	System.out.println("----------SampleReader postConstruct----------");
        try {
        	this.reader = new BufferedReader(new InputStreamReader(new ClassPathResource("com/example/batch/sample.tsv").getInputStream()));
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }

	@PreDestroy
	public void preDestroy() {
    	System.out.println("----------SampleReader preDestroy----------");
        try {
        	if (this.reader != null) {
        		this.reader.close();
        	}
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
	}
	
	@Override
	public Sample read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    	System.out.println("----------SampleReader read----------");
		
        String line = this.reader.readLine();
        if (line == null) {
        	this.reader.close();
        	return null;
        }
        
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
        
		return sample;
	}

}
