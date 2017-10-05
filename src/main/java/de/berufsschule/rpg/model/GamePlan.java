package de.berufsschule.rpg.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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
  private List<Skill> skills;

  public GamePlan() {
    this.pages = new ArrayList<>();
    this.items = new ArrayList<>();
    this.skills = new ArrayList<>();
  }
}
