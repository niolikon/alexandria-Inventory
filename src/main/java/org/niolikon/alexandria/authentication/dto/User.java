package org.niolikon.alexandria.authentication.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    private String username;
	
    private String firstname;

    private String lastname;
    
    private String facebookId;
    
    private List<String> roles;
}