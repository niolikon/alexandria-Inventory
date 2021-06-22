package org.niolikon.alexandria.inventory.catalog.book.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookRequest implements Serializable {

    /** Serial Version ID */
    private static final long serialVersionUID = -6428161225442573088L;

    @NotEmpty
    private String title;
    
    @NotEmpty
    private String synopsis;
    
    @NotEmpty
    private Long pages;
    
    @NotEmpty
    private String isbn;
    
    @NotNull
    private Long authorId;
    
    @NotNull
    private Long publisherId;

    @NotEmpty
	private String label;

    @NotEmpty
	private String image;

    @NotEmpty
	private BigDecimal price;
    
}
