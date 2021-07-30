package org.niolikon.alexandria.inventory.catalog.commons.dto;

import java.sql.Blob;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImageView {
    
    private long id;

    private String mimetype;
    
	private byte[] data;
}
