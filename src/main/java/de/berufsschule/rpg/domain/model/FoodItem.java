package de.berufsschule.rpg.domain.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FoodItem extends Item {

  private int value;
  private String type = "food";
}
