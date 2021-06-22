package org.niolikon.alexandria.inventory.catalog.commons.converter;

import org.niolikon.alexandria.inventory.catalog.commons.dto.PersonView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Person;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PersonToPersonViewConverter  implements Converter<Person, PersonView> {

    @Override
    public PersonView convert(@NonNull Person source) {
        PersonView view = new PersonView();
        view.setId(source.getId());
        view.setName(source.getName());
        view.setSurname(source.getSurname());
        return view;
    }

}
