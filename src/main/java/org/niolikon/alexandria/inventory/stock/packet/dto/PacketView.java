package org.niolikon.alexandria.inventory.stock.packet.dto;


import org.niolikon.alexandria.inventory.catalog.commons.dto.ProductView;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PacketView {
    
    private long id;
    
    private String location;

    private String size;

    private Integer weight;

    private String barcode;

    private String orderId;

    private String shipmentId;
    
    private ProductView product;
    
}
