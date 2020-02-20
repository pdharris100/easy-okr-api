package com.easyokr.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class Utils {
	
	public static long extractOrgId(JwtAuthenticationToken jat) {
		Long orgId = (Long)jat.getToken().getClaim("https://easy-okr.web.app/organisation");
		return orgId;
	}

}
