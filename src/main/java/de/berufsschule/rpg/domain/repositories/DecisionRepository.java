package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.Decision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisionRepository extends CrudRepository<Decision, Integer> {

}
