package de.berufsschule.rpg.domain.repositories;

import de.berufsschule.rpg.domain.model.User;
import de.berufsschule.rpg.domain.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);
}
