package de.berufsschule.rpg.dto;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.registration.annotations.PasswordMatches;
import de.berufsschule.rpg.registration.annotations.ValidEmail;
import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@PasswordMatches
public class UserDTO {

  private Integer id;
  @NotNull
  @NotEmpty
  @Size(min = 1, max = 30)
  private String username;

  @NotNull
  @NotEmpty
  @Size(min = 1, max = 50)
  @ValidEmail
  private String email;
  @NotNull
  @NotEmpty
  private String password;
  @NotNull
  @NotEmpty
  @Size(min = 1, max = 50)
  private String matchingPassword;
  private boolean firstVisit;
  private String currentGame;
  private List<Game> savedGames;

  private List<String> roles = new ArrayList<>();
  private boolean enabled;

}
