package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Decision {

  private String jump;
  private String alternativeJump;
  private int probability;
  private String text;
  private Integer injury = 0;
  private String neededItem;
  private Item item = new Item();
  private boolean hasItem = false;
}
