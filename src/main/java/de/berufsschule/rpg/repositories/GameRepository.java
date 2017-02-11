package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
  Game findByNameAndUserId(String name, Integer userId);
}
