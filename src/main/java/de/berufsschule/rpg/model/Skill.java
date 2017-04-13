package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer level;
  private Integer neededSkillPoints = 0;
  private Integer givenSkillPoints = 0;
  private Integer progress = 0;
  private String describtion;
  private String name;
}
