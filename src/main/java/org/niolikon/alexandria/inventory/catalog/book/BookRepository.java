package org.niolikon.alexandria.inventory.catalog.book;

import org.niolikon.alexandria.inventory.catalog.book.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Page<Book> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrIsbnEquals(String name, String description, String isbn, Pageable pageable);

	Page<Book> findByFeaturedTrue(Pageable pageable);
	
}
