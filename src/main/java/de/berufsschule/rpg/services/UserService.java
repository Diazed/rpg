package de.berufsschule.rpg.services;

import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.model.VerificationToken;
import de.berufsschule.rpg.repositories.UserRepository;
import de.berufsschule.rpg.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserService {


  private UserDTOConverter converter;
  private VerificationTokenRepository tokenRepository;
  private UserRepository userRepository;

  @Autowired
  public UserService(UserDTOConverter converter,
      VerificationTokenRepository tokenRepository,
      UserRepository userRepository) {
    this.converter = converter;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
  }

  public void editUser(User user) {
    userRepository.save(user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
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
    user.setFirstVisit(true);
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

  public VerificationToken getVerificationToken(String verificationToken) {
    return tokenRepository.findByToken(verificationToken);
  }

  public void createVerificationToken(User user, String token) {
    VerificationToken myToken = new VerificationToken();
    myToken.setToken(token);
    myToken.setUser(user);
    myToken.setExpiryDate(myToken.calculateExpiryDate(1440));
    tokenRepository.save(myToken);
  }
}
