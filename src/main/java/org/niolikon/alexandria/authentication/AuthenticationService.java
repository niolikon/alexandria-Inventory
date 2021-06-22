package org.niolikon.alexandria.authentication;

import java.util.Collections;

import org.niolikon.alexandria.authentication.converter.UserToUserDetailsConverter;
import org.niolikon.alexandria.authentication.dto.AuthenticationAnswer;
import org.niolikon.alexandria.authentication.dto.AuthenticationAnswer.AlexandriaAuthenticationError;
import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

@Log
@Service("alexandriaAuthenticationService")
public class AuthenticationService {

	@Autowired
	private AuthenticationConfig config;
    
    @Autowired
    private MessageProvider messageProvider;
    
    @Autowired
    public UserToUserDetailsConverter userToUserDetails; 

	public UserDetails checkJWTtoken(String jwtToken) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(jwtToken);
		
		AuthenticationAnswer answer = null;
		try {
			HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			ResponseEntity<AuthenticationAnswer> responseEntity = restTemplate.exchange(config.getCheckToken(),
					HttpMethod.GET, httpEntity, AuthenticationAnswer.class);
			answer = responseEntity.getBody();
			
			if (! answer.getSuccess()) {
				AlexandriaAuthenticationError err = answer.getErr();
				String errorMsg = messageProvider.getMessage("authentication.JWTCheckError", err.getName(), err.getMessage());
		        log.warning(errorMsg);
			}
		} catch (RestClientException _rce) {
			String errorMsg = messageProvider.getMessage("authentication.JWTCheckFailed");
	        log.warning(errorMsg);
		}

		if (answer != null) {
			return userToUserDetails.convert(answer.getUser());
		}

		return null;
	}
}
