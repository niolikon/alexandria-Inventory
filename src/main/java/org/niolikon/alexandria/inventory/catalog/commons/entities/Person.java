package org.niolikon.alexandria.inventory.catalog.commons.entities;

import java.util.Set;

import javax.persistence.*;

import org.niolikon.alexandria.inventory.catalog.book.entities.Book;
import org.springframework.data.domain.Persistable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@NoArgsConstructor
@Data
public class Person implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    
    @OneToMany(mappedBy="author",cascade=CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    Set<Book> books;

	@Override
	public boolean isNew() {
		if (this.id == null) {
			return true;
		}
		
		return false;
	}
}