package de.berufsschule.rpg.services;

import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
public class UserService {


  private UserDTOConverter converter;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  public UserService(UserDTOConverter converter) {
    this.converter = converter;
  }

  public void editUser(User user) {
    userRepository.save(user);
  }


  public User registerNewUserAccount(UserDTO userDTO, BindingResult result) {

    if (emailExist(userDTO.getEmail())) {
      result.reject("There is an account with that email adress: " + userDTO.getEmail());
    }
    if (usernameExist(userDTO.getUsername())) {
      result.reject("There is an account with that username: " + userDTO.getUsername());
    }
    if (result.hasErrors()) {
      return null;
    }

    User user = converter.toModel(userDTO);
    userRepository.save(user);
    return user;
  }

  private boolean emailExist(String email) {
    User user = userRepository.findByEmail(email);
    return user != null;
  }

  private boolean usernameExist(String username) {
    User user = userRepository.findByUsername(username);
    return user != null;
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }


  public User getRequestedUser(String username) {
    return userRepository.findByUsername(username);
  }
}
