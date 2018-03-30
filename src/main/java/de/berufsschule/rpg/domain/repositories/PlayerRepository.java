package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
