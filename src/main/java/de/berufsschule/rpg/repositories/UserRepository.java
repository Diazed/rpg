package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);

  User findByEmail(String email);
}
