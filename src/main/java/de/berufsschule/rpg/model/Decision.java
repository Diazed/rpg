package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Decision {

  private String jump;
  private String alternativeJump;
  private String usedItem;
  private int probability;
  private String requiredSkill;
  private Integer skillMinLvl;
  private Integer skillSuccessLvl;
  private Item item = new Item();
  private boolean hasItem = true;
  private boolean hasSkill = true;

  private String text;
}
