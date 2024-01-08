package com.example.batch.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.common.aspect.ServiceAspect;

@Aspect
@Component
public class BatchServiceAspect extends ServiceAspect {

	public BatchServiceAspect() {
	}

}
