package com.easyokr.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(
	  HttpServletRequest request,
	  HttpServletResponse response, 
	  Object handler) throws Exception {
		JwtAuthenticationToken jat = (JwtAuthenticationToken) request.getUserPrincipal();
		Long orgId = (Long)jat.getToken().getClaim("https://easy-okr.web.app/organisation");
		request.setAttribute("tenantId", orgId);
	    return true;
	}

}
