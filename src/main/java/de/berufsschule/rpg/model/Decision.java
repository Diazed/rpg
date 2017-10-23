package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Decision extends Possibility {

  private Integer mainJump;
  private Integer altJump;

}
