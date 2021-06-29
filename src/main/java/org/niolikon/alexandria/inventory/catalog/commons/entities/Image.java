package org.niolikon.alexandria.inventory.catalog.commons.entities;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@NoArgsConstructor
@Data
public class Image {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	
	@Column(name = "mimetype")
	private String mimetype;
	
	@Column(name = "data")
	@Lob
	private Blob data;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable=false, updatable=true)
    private Product product;
}
