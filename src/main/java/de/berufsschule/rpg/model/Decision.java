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
  private String requiredSkill;
  private Integer skillMinLvl;
  private Integer skillSuccessLvl;
  private Item item = new Item();
  private boolean hasItem = false;
  private boolean hasSkill = false;
}
