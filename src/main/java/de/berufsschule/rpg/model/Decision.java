package de.berufsschule.rpg.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Decision extends Possibility {

  private Integer mainJump;
  private Integer altJump;

  private String mainJumpName;
  private String altJumpName;

}
