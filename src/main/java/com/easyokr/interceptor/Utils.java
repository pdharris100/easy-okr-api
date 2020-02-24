package com.easyokr.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.easyokr.model.Organisation;

public class Utils {
	
	private static String ORG = "org";
	
	protected static HttpServletRequest setOrg(HttpServletRequest request, Organisation org) {
		request.setAttribute(ORG, org);
		return request;
	}
	
	public static Organisation getOrg(HttpServletRequest request) {
		Organisation org = (Organisation) request.getAttribute(ORG);
		return org;
	}

}
