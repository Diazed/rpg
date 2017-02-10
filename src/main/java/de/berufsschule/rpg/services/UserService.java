package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.repositories.UserRepository;
import de.berufsschule.rpg.validation.UserValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserService {

  private UserValidation userValidation;
  private UserRepository userRepository;

  public UserService(UserValidation userValidation, UserRepository userRepository) {
    this.userValidation = userValidation;
    this.userRepository = userRepository;
  }

  public void editUser(User user) {
    userRepository.save(user);
  }

  public void registerUser(User newUser, BindingResult bindingResult) {
    userValidation.validate(newUser, bindingResult);
    if (bindingResult.hasErrors())
      return;
    userRepository.save(newUser);
  }

  public User getRequestedUser(String username) {
    return userRepository.findByUsername(username);
  }
}
