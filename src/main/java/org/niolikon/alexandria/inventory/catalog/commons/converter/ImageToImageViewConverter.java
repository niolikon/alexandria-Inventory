package org.niolikon.alexandria.inventory.catalog.commons.converter;

import org.niolikon.alexandria.inventory.catalog.commons.dto.ImageView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Image;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ImageToImageViewConverter implements Converter<Image, ImageView>{

    @Override
    public ImageView convert(@NonNull Image source) {
    	ImageView view = new ImageView();
        view.setId(source.getId());
        view.setMimetype(source.getMimetype());
        view.setData(source.getData());
        return view;
    }
    
}
