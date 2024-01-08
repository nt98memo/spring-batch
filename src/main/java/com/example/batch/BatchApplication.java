package com.example.batch;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.common.CommonApplication;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan("com.example")
public class BatchApplication extends CommonApplication {

	public static void main(String[] args) {
		try {
			ConfigurableApplicationContext context = start(args, BatchApplication.class, "application,batch");
			System.exit(SpringApplication.exit(context));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}
