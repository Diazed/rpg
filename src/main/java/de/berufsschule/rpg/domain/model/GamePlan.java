package de.berufsschule.rpg.domain.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class GamePlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String filename;
  private String name;
  private Integer startPage;
  private Integer deathPage;
  private Integer roundHunger;
  private Integer roundThirst;
  private Integer roundExp;

  @OneToMany
  private List<Page> pages;
  @OneToMany
  private List<Item> items;
  @OneToMany
  private List<Skill> skills;

  public GamePlan() {
    this.pages = new ArrayList<>();
    this.items = new ArrayList<>();
    this.skills = new ArrayList<>();
  }
}
