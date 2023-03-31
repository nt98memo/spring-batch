package com.example.batch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.util.CurrentExecInfo;
import com.example.common.util.LogUtil;

@Component
public class LogUtilImpl implements LogUtil {
	
	@Autowired
	CurrentExecInfo currentExecInfoImpl;

	private static Logger LOG = LoggerFactory.getLogger("com.example.common.info");
	private static Logger WARN_LOG = LoggerFactory.getLogger("com.example.common.warn");
	private static Logger ERROR_LOG = LoggerFactory.getLogger("com.example.common.error");

	public LogUtilImpl() {
	}

	public void trace(String message) {
		LOG.trace(message);
	}

	public void traceUrl() {
		LOG.trace(getUrl());
	}

	public void debug(String message) {
		LOG.debug(message);
	}

	public void debugUrl() {
		LOG.debug(getUrl());
	}

	public void info(String message) {
		LOG.info(message);
	}

	public void infoUrl() {
		LOG.info(getUrl());
	}

	public void warn(String message) {
		LOG.warn(message);
		WARN_LOG.warn(message);
	}

	public void warnUrl() {
		LOG.warn(getUrl());
		WARN_LOG.warn(getUrl());
	}

	public void error(String message) {
		LOG.error(message);
		WARN_LOG.error(message);
		ERROR_LOG.error(message);
	}

	public void errorUrl() {
		LOG.error(getUrl());
		WARN_LOG.error(getUrl());
		ERROR_LOG.error(getUrl());
	}

	public String getUrl() {

		return currentExecInfoImpl.getExecName();

	}

	public void writeException(Exception e) {

		StackTraceElement[] traceArray = e.getStackTrace();
		for (StackTraceElement trace : traceArray) {
			error(trace.toString());
		}

	}

}
