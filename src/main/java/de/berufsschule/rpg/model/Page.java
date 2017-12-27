package de.berufsschule.rpg.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Page {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @Column(length = 300)
  private String storytext;
  @OneToMany
  private List<Possibility> possibilities;
  @ManyToMany
  private List<Item> items;
  @ManyToMany
  private List<Skill> skills;
  private boolean checkpoint = false;
  private String usedItem = "";

  // TODO: Manipulation Class ?
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
