package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkItem extends Item {
  private int value;
  private boolean drink = true;
  private boolean consumable = true;
}
