package com.example.batch;

import java.io.IOException;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.common.CommonApplication;

@SpringBootApplication
@EnableBatchProcessing
@EnableAspectJAutoProxy
@ComponentScan("com.example")
public class BatchApplication extends CommonApplication {

	public static void main(String[] args) {
		try {
			start(args, BatchApplication.class, "application,batch");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
