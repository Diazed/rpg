package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);
}
