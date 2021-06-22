package org.niolikon.alexandria.inventory.catalog.book;

import org.niolikon.alexandria.inventory.catalog.book.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
