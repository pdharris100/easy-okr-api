package com.easyokr.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.easyokr.model.Organisation;
import com.easyokr.repository.OrganisationRepository;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {
	
	public MultiTenancyInterceptor(OrganisationRepository orgRepository) {
		this.orgRepository = orgRepository;
	}
	
	private OrganisationRepository orgRepository;
	
	@Override
	public boolean preHandle(
	  HttpServletRequest request,
	  HttpServletResponse response, 
	  Object handler) throws Exception {
		JwtAuthenticationToken jat = (JwtAuthenticationToken) request.getUserPrincipal();
		Long orgId = (Long)jat.getToken().getClaim("https://easy-okr.web.app/organisation");
		Optional<Organisation> opt = orgRepository.findById(orgId);
		if (opt.isPresent()) {
			Utils.setOrg(request, opt.get());
			return true;
		}
	    return false;
	}

}

