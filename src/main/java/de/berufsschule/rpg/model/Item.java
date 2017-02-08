package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorValue("Main")
@DiscriminatorColumn(name="Kind")
@Getter
@Setter
public class Item {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private boolean consumable;
  private String name;
  private String description;
  private Integer amount;
  private Integer playerId;
}
