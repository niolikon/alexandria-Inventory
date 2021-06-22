package org.niolikon.alexandria.inventory.security;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.niolikon.alexandria.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.java.Log;

@Log
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	final static String AUTHORIZATION_HEADER = "Authorization";
	final static String TOKEN_PREFIX = "Bearer ";
	
    @Autowired
    AuthenticationService authenticationService; 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException 
	{
		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		if ((authorizationHeader == null) || (! authorizationHeader.contains(TOKEN_PREFIX))) {
			log.warning("Could not retrieve authorization header");
			chain.doFilter(request, response);
			return;
		}
		
		String jwtToken = authorizationHeader.substring(TOKEN_PREFIX.length());
		UserDetails userDetails = authenticationService.checkJWTtoken(jwtToken);
		if (userDetails == null) {
			log.warning("Could not authenticate user");
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		chain.doFilter(request, response);
	}
}
