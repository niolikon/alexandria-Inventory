package org.niolikon.alexandria.inventory.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/** */
	private static final long serialVersionUID = -8216994676149157434L;
    
    @Autowired
    private MessageProvider messageProvider;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException 
	{
		String errorMsg = messageProvider.getMessage("authentication.JWTMissing");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMsg);
	}

}
