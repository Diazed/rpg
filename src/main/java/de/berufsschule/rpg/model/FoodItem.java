package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
@DiscriminatorValue("Food")
@Getter
@Setter
public class FoodItem extends Item {
  private int value;
  private boolean drink = false;
}
