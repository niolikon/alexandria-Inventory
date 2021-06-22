package org.niolikon.alexandria.authentication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthenticationAnswer {

	@NoArgsConstructor
	@Data
	public class AlexandriaAuthenticationError {

		private String name;

		private String message;
	}

	private String status;

	private Boolean success;

	private User user;

	private AlexandriaAuthenticationError err;
}
