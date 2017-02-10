package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
  User findByUsername(String username);
}
