package de.berufsschule.rpg.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDTO {

  private Integer id;
  private Integer level = 0;
  private Integer neededSkillPoints = 1;
  private Integer givenSkillPoints = 0;
  private Integer progress = 0;
  private String describtion;
  private String name;

}
