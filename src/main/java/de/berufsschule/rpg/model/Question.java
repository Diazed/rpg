package de.berufsschule.rpg.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question extends Possibility {

  private boolean asked = false;
  private boolean takeAlt = false;
  private String mainAnswer;
  private String altAnswer;

}
