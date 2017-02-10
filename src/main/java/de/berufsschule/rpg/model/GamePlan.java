package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GamePlan {
  private String name;
  private String startPage;
  private String deathPage;
  private List<Page> pages;
  private List<Item> items;
}
