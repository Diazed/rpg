package de.berufsschule.rpg.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO {

  private Integer id;
  private String name;
  private String storytext;
  private List<PossibilityDTO> possibilities;
  private List<ItemDTO> items;
  private List<SkillDTO> skills;
  private boolean checkpoint = false;
  private String usedItem = "";

  // TODO: Manipulation Class ?
  private Integer healthManipulation;
  private Integer hungerManipulation;
  private Integer thirstManipulation;
  private Integer xpManipulation;
  private Integer skillPointManipulation;

}
