package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Setter
@Getter
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String startPage;
  private String deathPage;
  @Transient
  private HashMap<String, Page> pages;
  @Transient
  private HashMap<String, Item> items;
  @OneToOne
  @Cascade(CascadeType.ALL)
  private Player player;
}
