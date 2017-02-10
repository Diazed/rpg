package de.berufsschule.rpg.validation;

import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Component
public class UserValidation {

  public final static String ERROR_EXISTING_NAME = "Der Name exestiert bereits.";
  private UserRepository userRepository;
  private Validator validator;

  @Autowired
  public UserValidation(UserRepository userRepository, Validator validator) {
    this.userRepository = userRepository;
    this.validator = validator;
  }

  public void validate(User newUser, BindingResult bindingResult) {
    validator.validate(newUser, bindingResult);
    if (bindingResult.hasErrors()) {
      return;
    }
    User existingUser = userRepository.findByUsername(newUser.getUsername());
    if ((existingUser != null) && (existingUser.getId() != newUser.getId())) {
      bindingResult.rejectValue("username", "", ERROR_EXISTING_NAME);
    }
  }

}
