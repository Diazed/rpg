package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    public Player findByUsername(String username);
}
