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
  private Integer userId;
  private String name;
  private String startPage;
  private String deathPage;
  private Integer roundHunger;
  private Integer roundThirst;
  private Integer roundExp;
  @Transient
  private HashMap<String, Page> pages = new HashMap<String, Page>();
  @Transient
  private HashMap<String, Item> items = new HashMap<String, Item>();
  @OneToOne(mappedBy = "game")
  @Cascade(CascadeType.ALL)
  private Player player;
}
