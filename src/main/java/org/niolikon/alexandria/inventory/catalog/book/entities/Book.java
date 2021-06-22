package org.niolikon.alexandria.inventory.catalog.book.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.niolikon.alexandria.inventory.catalog.commons.entities.Company;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Person;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Product;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@DiscriminatorValue("book")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public class Book extends Product {
	
    public String getTitle() {
    	return super.getName();
    };

    public void setTitle(String title) {
    	super.setName(title);
    };
    
    public String getSynopsis() {
    	return super.getDescription();
    };

    public void setSynopsis(String synopsis) {
    	super.setDescription(synopsis);
    };

	@Column(name = "book_pages")
	private Long pages;
	
	@Column(name = "book_isbn")
	private String isbn;

    @ManyToOne
    @JoinColumn(name = "book_author_id", nullable=false, updatable=true)
    private Person author;

    @ManyToOne
    @JoinColumn(name = "book_publisher_id", nullable=false, updatable=true)
    private Company publisher;
}
