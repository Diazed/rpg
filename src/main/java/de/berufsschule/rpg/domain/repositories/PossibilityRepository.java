package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.Possibility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibilityRepository extends CrudRepository<Possibility, Integer> {

}
