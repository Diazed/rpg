package de.berufsschule.rpg.domain.dto;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Skill;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerDTO {

  private Integer id;
  private Integer checkpoint;
  private Integer position;
  private Integer playerLvl = 0;
  private Integer hitpoints = 100;
  private Integer hunger = 0;
  private Integer thirst = 0;
  private Integer exp = 0;
  private Integer neededExp = 0;
  private Integer progressPercentage;
  private Integer skillPoints;
  private List<Item> items = new ArrayList<>();
  private List<Skill> skills = new ArrayList<>();

}
