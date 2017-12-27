package de.berufsschule.rpg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PossibilityDTO {

  private Integer id;
  private Integer probability;
  private String usedItem;
  private boolean hasItem = true;
  private String requiredSkill;
  private Integer skillMinLvl;
  private Integer skillSuccessLvl;
  private boolean hasSkill = true;
  private String text;

}
