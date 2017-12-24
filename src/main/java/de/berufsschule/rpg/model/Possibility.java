package de.berufsschule.rpg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Possibility {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
