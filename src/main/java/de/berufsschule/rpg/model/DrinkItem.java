package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkItem extends Item {
  private int value;
  private String type = "drink";
}
