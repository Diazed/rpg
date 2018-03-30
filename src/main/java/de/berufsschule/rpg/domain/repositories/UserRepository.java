package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
  User findByEmail(String email);
}
