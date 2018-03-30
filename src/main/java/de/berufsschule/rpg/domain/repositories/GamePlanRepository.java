package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.GamePlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlanRepository extends CrudRepository<GamePlan, Integer> {

}
