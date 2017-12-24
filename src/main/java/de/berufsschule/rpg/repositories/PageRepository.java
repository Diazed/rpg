package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends CrudRepository<Page, Integer> {

}
