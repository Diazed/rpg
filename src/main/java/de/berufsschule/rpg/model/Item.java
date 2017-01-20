package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Item {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private Integer healing;
  private boolean consumable;
  private boolean drink;
  private String name;
  private String description;
  private Integer value;
  private Integer amount;
  private Integer playerId;
}
