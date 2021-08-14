package org.niolikon.alexandria.inventory.catalog.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.niolikon.alexandria.inventory.catalog.book.converter.BookToBookViewConverter;
import org.niolikon.alexandria.inventory.catalog.book.dto.BookRequest;
import org.niolikon.alexandria.inventory.catalog.book.dto.BookView;
import org.niolikon.alexandria.inventory.catalog.book.entities.Book;
import org.niolikon.alexandria.inventory.catalog.commons.CompanyRepository;
import org.niolikon.alexandria.inventory.catalog.commons.PersonRepository;
import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.niolikon.alexandria.inventory.system.exceptions.EntityDuplicationException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    private final PersonRepository authorRepo;
    private final BookRepository bookRepo;
    private final CompanyRepository publisherRepo;
    private final BookToBookViewConverter bookConverter;
    private final MessageProvider messageProvider;

    public BookService(PersonRepository authorRepo,
            BookRepository bookRepo,
            CompanyRepository publisherRepo,
            BookToBookViewConverter bookConverter,
            MessageProvider messageUtil) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.publisherRepo = publisherRepo;
        this.bookConverter = bookConverter;
        this.messageProvider = messageUtil;
    }
    
    public Book findBookOrThrow(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageProvider.getMessage("book.NotFound", id)));
    }
    
    public BookView getBook(Long id) {
        Book book = findBookOrThrow(id);
        return bookConverter.convert(book);
    }

    public Page<BookView> findAllBooks(Pageable pageable) {
        return this.findAllBooksMatching(Optional.empty(), pageable);
    }

    public Page<BookView> findAllBooksMatching(Optional<String> search, Pageable pageable) {
        Page<Book> books;
        if (search.isPresent()) {
        	books = bookRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrIsbnEquals(search.get(), search.get(), search.get(), pageable);
        }
        else {
        	books = bookRepo.findAll(pageable);
        }
        
        List<BookView> bookViews = books.stream()
        		.map( book -> bookConverter.convert(book))
        		.collect(Collectors.toList());
        
        return new PageImpl<>(bookViews, pageable, books.getTotalElements());
    }

    public Page<BookView> findFeaturedBooks(Pageable pageable) {
        Page<Book> books = bookRepo.findByFeaturedTrue(pageable);
        
        List<BookView> bookViews = books.stream()
        		.map( book -> bookConverter.convert(book))
        		.collect(Collectors.toList());
        
        return new PageImpl<>(bookViews, pageable, books.getTotalElements());
    }
    
    @Transactional
    public BookView create(BookRequest req) {
        Book book = new Book();
        this.fetchFromRequest(book, req);
        
        try {
            Book bookSaved = bookRepo.save(book);
            return bookConverter.convert(bookSaved);
        } catch (DataIntegrityViolationException e) {
            throw new EntityDuplicationException(messageProvider.getMessage("book.Duplication", book.getTitle()));
        }
    }
    
    @Transactional
    public void delete(Long id) {
        try {
            bookRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageProvider.getMessage("book.NotFound", id));
        }
    }
    
    public BookView update(Book book, BookRequest req) {
        Book bookUpdated = this.fetchFromRequest(book, req);
        Book bookSaved = bookRepo.save(bookUpdated);
        return bookConverter.convert(bookSaved);
    }
    
    private Book fetchFromRequest(Book book, BookRequest req) {
        book.setTitle(req.getTitle());
        book.setSynopsis(req.getSynopsis());
        book.setPages(req.getPages());
        book.setIsbn(req.getIsbn());
        book.setAuthor(authorRepo.getById(req.getAuthorId()));
        book.setPublisher(publisherRepo.getById(req.getPublisherId()));
        book.setLabel(req.getLabel());
        book.setPrice(req.getPrice());
        
        if (req.getFeatured() == null) {
        	book.setFeatured(Boolean.FALSE);
        }
        else {
        	book.setFeatured(req.getFeatured());
        }
        
        return book;
    }
}
