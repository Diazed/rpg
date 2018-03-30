package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {

}
