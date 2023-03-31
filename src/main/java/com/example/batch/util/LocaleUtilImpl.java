package com.example.batch.util;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.example.common.util.LocaleUtil;

@Component
public class LocaleUtilImpl implements LocaleUtil {

	private static Locale STATIC_LOCALE = Locale.ENGLISH;

	public LocaleUtilImpl() {
	}

	public Locale getRequestLocale() {
		return getRequestLocale(STATIC_LOCALE);
	}

	public Locale getRequestLocale(Locale defaultLocale) {

		return Locale.getDefault();

	}

}
