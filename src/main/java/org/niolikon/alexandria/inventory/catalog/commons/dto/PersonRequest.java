package org.niolikon.alexandria.inventory.catalog.commons.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonRequest implements Serializable {
    
    /** Serial Version ID */
    private static final long serialVersionUID = -6152631506716661299L;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String surname;
    
}
