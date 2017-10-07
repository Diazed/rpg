package de.berufsschule.rpg.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

  private String name;
  private String storytext;
  private List<Possibility> possibilities;
  private List<String> items;
  private List<String> skills;
  private boolean checkpoint = false;
  private String usedItem = "";
  private Integer healthManipulation;
  private Integer hungerManipulation;
  private Integer thirstManipulation;
  private Integer xpManipulation;
  private Integer skillPointManipulation;

  public Page() {
    this.possibilities = new ArrayList<>();
    this.items = new ArrayList<>();
    this.skills = new ArrayList<>();
  }
}
