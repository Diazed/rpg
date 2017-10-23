package de.berufsschule.rpg.model;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Setter
@Getter
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer userId;
  private String name;
  private Integer startPage;
  private Integer deathPage;
  private Integer roundHunger;
  private Integer roundThirst;
  private Integer roundExp;
  @Transient
  private HashMap<Integer, Page> pages = new HashMap<>();
  @Transient
  private HashMap<String, Item> items = new HashMap<>();
  @OneToOne(mappedBy = "game")
  @Cascade(CascadeType.ALL)
  private Player player;
}
