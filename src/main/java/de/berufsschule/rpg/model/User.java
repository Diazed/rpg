package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @NotNull
  @Size(min=1, max=30)
  private String username;
  @NotNull
  @Size(min=1, max=30)
  private String password;
  private String role = "ROLE_USER";
  private boolean enabled = true;

}
