package com.dev.springsecurity.utility;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static String getApplicationUrl(HttpServletRequest request) {
		return "http://"
				+ request.getServerName()
				+ ":"
				+ request.getServerPort()
				+ request.getContextPath();
	}

}
