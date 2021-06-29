package org.niolikon.alexandria.inventory.catalog.commons;

import org.niolikon.alexandria.inventory.catalog.commons.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
