package org.niolikon.alexandria.inventory.stock.packet.entities;

import javax.persistence.*;

import org.niolikon.alexandria.inventory.catalog.commons.entities.Product;
import org.springframework.data.domain.Persistable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "packet")
@NoArgsConstructor
@Data
public class Packet implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "location")
    private String location;

    @Column(name = "size")
    private String size;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "order_eid")
    private String orderId;

    @Column(name = "shipment_eid")
    private String shipmentId;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable=false, updatable=true)
    private Product product;

	@Override
	public boolean isNew() {
		if (this.id == null) {
			return true;
		}
		
		return false;
	}
}