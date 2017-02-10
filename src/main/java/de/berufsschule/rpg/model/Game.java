package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Setter
@Getter
public class Game {

  private String name;
  private String startPage;
  private String deathPage;
  private List<Page> pages;
  private List<Item> items;
}
