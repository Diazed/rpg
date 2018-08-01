package de.berufsschule.rpg.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Decision {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer probability;
  private String usedItem;
  private boolean hasItem = true;
  private Integer requiredSkillId;
  private String requiredSkill;
  private Integer skillMinLvl;
  private Integer skillSuccessLvl;
  private boolean hasSkill = true;

  private Integer mainJump;
  private Integer altJump;

  private String mainJumpName;
  private String altJumpName;

  private String answer;
  private String altAnswer;

  private Boolean wasClicked = false;

  private String text;

  private Boolean isQuestion;

}
