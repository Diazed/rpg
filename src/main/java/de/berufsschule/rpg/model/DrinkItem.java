package de.berufsschule.rpg.model;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DrinkItem extends Item {
  private int value;
  private String type = "drink";
}
