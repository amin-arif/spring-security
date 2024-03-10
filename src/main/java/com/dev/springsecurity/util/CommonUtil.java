package com.dev.springsecurity.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {

	public static String getApplicationUrl(HttpServletRequest request) {
		return "http://"
				+ request.getServerName()
				+ ":"
				+ request.getServerPort()
				+ request.getContextPath();
	}

	public static String generateToken() {
		return UUID.randomUUID().toString();
	}

	public static Date generateExpirationTime(int tokenExpirationTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, tokenExpirationTime); // In minutes
		return new Date(calendar.getTime().getTime());
	}

	public static Boolean isTimeExpire(Date expirationTime) {
		Date currentTime = Calendar.getInstance().getTime();
		if (currentTime.getTime() - expirationTime.getTime() >= 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
