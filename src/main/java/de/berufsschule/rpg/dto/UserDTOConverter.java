package de.berufsschule.rpg.dto;

import de.berufsschule.rpg.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

  public UserDTO toDto(User model) {
    UserDTO dto = new UserDTO();

    dto.setEnabled(model.isEnabled());
    dto.setId(model.getId());
    dto.setPassword(model.getPassword());
    dto.setRole(model.getRole());
    dto.setUsername(model.getUsername());
    dto.setCurrentGame(model.getCurrentGame());
    dto.setSavedGames(model.getSavedGames());

    return dto;
  }

  public User toModel(UserDTO dto) {
    User model = new User();

    model.setEnabled(dto.isEnabled());
    model.setId(dto.getId());
    model.setPassword(dto.getPassword());
    model.setRole(dto.getRole());
    model.setUsername(dto.getUsername());
    model.setCurrentGame(dto.getCurrentGame());
    model.setSavedGames(dto.getSavedGames());

    return model;
  }


}
