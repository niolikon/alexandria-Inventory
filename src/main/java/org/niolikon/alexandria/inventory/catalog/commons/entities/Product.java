package org.niolikon.alexandria.inventory.catalog.commons.entities;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.niolikon.alexandria.inventory.stock.packet.entities.Packet;
import org.springframework.data.domain.Persistable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
@NoArgsConstructor
@Data
public class Product implements Persistable<Long> {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "label")
	private String label;
	
	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "featured")
	private Boolean featured;
	
	@OneToMany(mappedBy="product", cascade=CascadeType.REMOVE)
	@EqualsAndHashCode.Exclude
	Set<Packet> packets;

	@OneToMany(mappedBy="product", cascade=CascadeType.REMOVE)
	@EqualsAndHashCode.Exclude
	Set<Image> images;
	
	@Override
	public boolean isNew() {
		if (this.id == null) {
			return true;
		}
		
		return false;
	}
}
