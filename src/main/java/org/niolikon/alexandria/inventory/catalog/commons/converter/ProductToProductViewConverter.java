package org.niolikon.alexandria.inventory.catalog.commons.converter;

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
        view.setImage(source.getImage());
        return view;
    }

}
