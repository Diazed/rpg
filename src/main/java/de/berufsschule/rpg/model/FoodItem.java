package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItem extends Item {
  private int value;
  private boolean drink = false;
  private boolean consumable = true;
}
