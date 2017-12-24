package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.Possibility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossibilityRepository extends CrudRepository<Possibility, Integer> {

}
