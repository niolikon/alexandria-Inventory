package org.niolikon.alexandria.inventory.catalog.book.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.niolikon.alexandria.inventory.catalog.commons.dto.CompanyView;
import org.niolikon.alexandria.inventory.catalog.commons.dto.PersonView;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookView {
    
    private long id;
    
    private String title;
    
    private String synopsis;
    
    private Long pages;
    
    private String isbn;
    
    private PersonView author;
    
    private CompanyView publisher;
	
	private String label;
	
	private BigDecimal price;
	
	private List<Long> imageIds;
	
	private long availability;
	
	private Boolean featured;
}
