package de.berufsschule.rpg.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserDTO {

  private Integer id;
  @Size(min = 1, max = 30)
  private String username;
  @Size(min = 1, max = 30)
  private String password;
  private String currentGame;
  private List<String> savedGames;
  private String role = "ROLE_USER";
  private boolean enabled = true;

}
