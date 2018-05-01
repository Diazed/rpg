package de.berufsschule.rpg.domain.dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GamePlanDTO {

  private Integer id;
  private String filename;
  private String name;
  private Integer startPage;
  private Integer deathPage;
  private Integer roundHunger;
  private Integer roundThirst;
  private Integer roundExp;
  private List<PageDTO> pages;
  private List<ItemDTO> items;
  private List<SkillDTO> skills;

}
