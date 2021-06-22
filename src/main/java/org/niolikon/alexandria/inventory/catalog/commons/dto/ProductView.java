package org.niolikon.alexandria.inventory.catalog.commons.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductView {
    
    private long id;

    private String name;

	private String description;
	
	private String label;
	
	private String image;
	
	private BigDecimal price;

}
