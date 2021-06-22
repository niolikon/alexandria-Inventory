package org.niolikon.alexandria.inventory.catalog.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductRequest implements Serializable {

	/** Serial Version ID */
	private static final long serialVersionUID = 7413602318541965573L;

    @NotEmpty
    private String name;

    @NotEmpty
	private String description;

    @NotEmpty
	private String label;

    @NotEmpty
	private String image;

    @NotEmpty
	private BigDecimal price;
}
