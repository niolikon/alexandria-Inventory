package org.niolikon.alexandria.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("alexandria.authentication")
@Data
public class AuthenticationConfig {
	private String checkToken;
}