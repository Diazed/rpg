package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
}
