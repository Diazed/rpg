package de.berufsschule.rpg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
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
  @Column(length = 65)
  private String password;
  @NotNull
  @NotEmpty
  @Size(min = 1, max = 50)
  private String matchingPassword;

  private boolean firstVisit = false;

  private Locale prefLocale = Locale.ENGLISH;

  private Integer currentGame;
  @ManyToMany
  private List<Game> savedGames;
  @ElementCollection
  private List<String> roles = new ArrayList<>();
  private boolean enabled;

  public User() {
    this.enabled = false;
  }

}
