package org.niolikon.alexandria.authentication.converter;

import org.niolikon.alexandria.authentication.dto.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {
	
	@Override
    public UserDetails convert(@NonNull User source) {
        UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(source.getUsername());
		builder.password("");
		
		String[] authorities = source.getRoles().stream()
				.map(role -> "ROLE_" + role.toUpperCase())
				.toArray(String[]::new);
		builder.authorities(authorities);
		
		return builder.build();
    }
}
