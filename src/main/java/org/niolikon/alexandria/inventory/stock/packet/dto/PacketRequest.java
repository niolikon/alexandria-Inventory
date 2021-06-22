package org.niolikon.alexandria.inventory.stock.packet.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PacketRequest implements Serializable {

	/** Serial Version ID */
	private static final long serialVersionUID = 352529563435318709L;

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String surname;

    @NotEmpty
    private String location;

    @NotEmpty
    private String size;
    
    @NotNull
    private Integer weight;

    @NotEmpty
    private String barcode;

    @NotNull
    private Long productId;

    private String orderId;

    private String shipmentId;
    
}
