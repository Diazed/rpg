package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Item {

  private boolean consumable;
  private int amount;
  private String name;
  private String description;


}
