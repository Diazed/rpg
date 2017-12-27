package de.berufsschule.rpg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

  private Integer id;
  private boolean consumable;
  private int amount;
  private String name;
  private String description;

}
