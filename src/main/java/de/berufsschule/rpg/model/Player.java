package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String checkpoint;
  private String position;
  private Boolean alive;
  private Boolean onDeathPage;
  private Integer hitpoints;
  private Integer hunger;
  private Integer thirst;
  private Integer exp;
  private Integer playerLvl;
  private Integer neededExp;
  private Integer levelProgress;
  @OneToOne
  @PrimaryKeyJoinColumn
  private Game game;
  @ElementCollection
  private List<String> items = new ArrayList<>();
}
