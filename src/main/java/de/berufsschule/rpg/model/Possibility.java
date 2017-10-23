package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Possibility {

  private Integer ID;
  private Integer probability;
  private String usedItem;
  private boolean hasItem = true;
  private String requiredSkill;
  private Integer skillMinLvl;
  private Integer skillSuccessLvl;
  private boolean hasSkill = true;

  private String text;
}