package org.niolikon.alexandria.inventory.catalog.commons;

import org.niolikon.alexandria.inventory.catalog.commons.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
