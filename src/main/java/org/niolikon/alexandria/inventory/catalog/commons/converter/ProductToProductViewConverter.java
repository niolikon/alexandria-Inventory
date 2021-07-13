package org.niolikon.alexandria.inventory.catalog.commons.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.niolikon.alexandria.inventory.catalog.book.entities.Book;
import org.niolikon.alexandria.inventory.catalog.commons.dto.ProductView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductViewConverter  implements Converter<Product, ProductView> {

    @Override
    public ProductView convert(@NonNull Product source) {
    	ProductView view = new ProductView();
        view.setId(source.getId());
        view.setName(source.getName());
        view.setDescription(source.getDescription());
        view.setPrice(source.getPrice());
        view.setLabel(source.getLabel());
        
        if ( source.getImages() == null) {
        	view.setImageIds( List.of());
        }
        else {
            view.setImageIds( source.getImages().stream()
            					.map( image -> image.getId())
            					.collect(Collectors.toList()) );
        }
        
        if (source instanceof Book) {
        	view.setType("book");
        }
        else {
        	view.setType("");
        }
        
        if ( source.getPackets() == null) {
        	view.setAvailability(0);
        }
        else {
        	view.setAvailability( source.getPackets().stream()
									.filter(packet -> (packet.getOrderId() == null))
        							.count() );
        }
        
        return view;
    }

}
