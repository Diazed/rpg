package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
@DiscriminatorValue("Drink")
@Getter
@Setter
public class DrinkItem extends Item {
  private int value;
  private boolean drink = true;
}
