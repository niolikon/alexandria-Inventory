package org.niolikon.alexandria.inventory.catalog.book.converter;

import org.niolikon.alexandria.inventory.catalog.book.dto.BookView;
import org.niolikon.alexandria.inventory.catalog.book.entities.Book;
import org.niolikon.alexandria.inventory.catalog.commons.converter.CompanyToCompanyViewConverter;
import org.niolikon.alexandria.inventory.catalog.commons.converter.PersonToPersonViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookToBookViewConverter implements Converter<Book, BookView> {

    @Autowired
    PersonToPersonViewConverter authorConverter;
    
    @Autowired
    CompanyToCompanyViewConverter publisherConverter;
    
    @Override
    public BookView convert(Book source) {
        BookView view = new BookView();
        view.setId(source.getId());
        view.setTitle(source.getTitle());
        view.setSynopsis(source.getSynopsis());
        view.setPages(source.getPages());
        view.setIsbn(source.getIsbn());
        view.setAuthor(authorConverter.convert(source.getAuthor()));
        view.setPublisher(publisherConverter.convert(source.getPublisher()));
        view.setLabel(source.getLabel());
        view.setImage(source.getImage());
        view.setPrice(source.getPrice());
        return view;
    }
    
}
