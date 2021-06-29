package org.niolikon.alexandria.inventory.catalog.commons.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyRequest implements Serializable {
    
    /** Serial Version ID */
    private static final long serialVersionUID = -4023001739185863141L;
    
    @NotEmpty
    private String name;
    
}
