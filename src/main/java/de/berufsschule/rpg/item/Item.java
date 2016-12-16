package de.berufsschule.rpg.item;

import de.berufsschule.rpg.player.Player;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Item {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private boolean consumable;
  private boolean drink;
  private String name;
  private String description;
  private Integer value;
  private Integer amount;
  private Integer playerId;
}
