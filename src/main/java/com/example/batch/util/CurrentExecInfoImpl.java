package com.example.batch.util;

import org.springframework.stereotype.Component;

import com.example.common.util.CurrentExecInfo;

@Component
public class CurrentExecInfoImpl implements CurrentExecInfo {

	@Override
	public String getExecName() {

		return "batch";

	}

}
