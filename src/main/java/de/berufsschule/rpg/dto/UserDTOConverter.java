package de.berufsschule.rpg.dto;

import de.berufsschule.rpg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserDTOConverter(
      PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public UserDTO toDto(User model) {
    UserDTO dto = new UserDTO();

    dto.setFirstVisit(model.isFirstVisit());
    dto.setEnabled(model.isEnabled());
    dto.setId(model.getId());
    dto.setPassword(model.getPassword());
    dto.setRoles(model.getRoles());
    dto.setUsername(model.getUsername());
    dto.setCurrentGame(model.getCurrentGame());
    dto.setSavedGames(model.getSavedGames());
    dto.setMatchingPassword(model.getMatchingPassword());
    dto.setEmail(model.getEmail());

    return dto;
  }

  public User toModel(UserDTO dto) {
    User model = new User();

    model.setFirstVisit(dto.isFirstVisit());
    model.setEnabled(dto.isEnabled());
    model.setId(dto.getId());
    model.setPassword(passwordEncoder.encode(dto.getPassword()));
    model.setMatchingPassword(dto.getMatchingPassword());
    model.setRoles(dto.getRoles());
    model.setUsername(dto.getUsername());
    model.setEmail(dto.getEmail());
    model.setCurrentGame(dto.getCurrentGame());
    model.setSavedGames(dto.getSavedGames());

    return model;
  }


}
