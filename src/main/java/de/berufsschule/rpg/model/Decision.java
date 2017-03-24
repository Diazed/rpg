package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Decision {

  private String jump;
  private String alternativeJump;
  private int probability;
  private String text;
  private String usedItem;
  private Item item = new Item();
  private boolean hasItem = false;
}
