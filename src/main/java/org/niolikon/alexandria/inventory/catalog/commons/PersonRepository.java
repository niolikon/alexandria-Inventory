package org.niolikon.alexandria.inventory.catalog.commons;

import org.niolikon.alexandria.inventory.catalog.commons.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
