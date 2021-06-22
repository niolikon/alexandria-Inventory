package org.niolikon.alexandria.inventory.catalog.commons.converter;

import org.niolikon.alexandria.inventory.catalog.commons.dto.CompanyView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Company;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyToCompanyViewConverter implements Converter<Company, CompanyView>{

    @Override
    public CompanyView convert(@NonNull Company source) {
        CompanyView view = new CompanyView();
        view.setId(source.getId());
        view.setName(source.getName());
        return view;
    }
    
}
