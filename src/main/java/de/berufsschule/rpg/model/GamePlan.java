package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GamePlan {
  private String filename;
  private String name;
  private String startPage;
  private String deathPage;
  private Integer roundHunger;
  private Integer roundThirst;
  private Integer roundExp;
  private List<Page> pages;
  private List<Item> items;
}
