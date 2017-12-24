package de.berufsschule.rpg.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.action.internal.OrphanRemovalAction;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Getter
@Setter
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer checkpoint;
  private Integer position;
  private Boolean alive;
  private Boolean onDeathPage;
  private Integer hitpoints;
  private Integer hunger;
  private Integer thirst;
  private Integer exp;
  private Integer playerLvl;
  private Integer neededExp = 0;
  private Integer levelProgress;
  private Integer skillPoints = 0;
  @OneToOne
  @PrimaryKeyJoinColumn
  private Game game;
  @ManyToMany()
  @Cascade(CascadeType.ALL)
  private List<Item> items = new ArrayList<>();
  @ManyToMany
  private List<Skill> skills = new ArrayList<>();

}
