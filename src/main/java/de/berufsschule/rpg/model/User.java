package de.berufsschule.rpg.model;

import java.util.ArrayList;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotNull
  @NotEmpty
  @Size(min=1, max=30)
  private String username;
  @NotNull
  @NotEmpty
  @Size(min = 1, max = 50)
  private String email;
  @NotNull
  @NotEmpty
  private String password;
  @NotNull
  @NotEmpty
  @Size(min = 1, max = 50)
  private String matchingPassword;

  private boolean firstVisit = false;

  private Locale prefLocale = Locale.ENGLISH;

  private String currentGame;
  @ElementCollection
  private List<String> savedGames;
  @ElementCollection
  private List<String> roles = new ArrayList<>();
  private boolean enabled;

  public User() {
    this.enabled = false;
  }

}
