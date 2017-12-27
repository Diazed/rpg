package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.GamePlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlanRepository extends CrudRepository<GamePlan, Integer> {

}
