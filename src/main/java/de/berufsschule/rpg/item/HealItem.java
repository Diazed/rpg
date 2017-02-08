package de.berufsschule.rpg.item;

import de.berufsschule.rpg.model.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
@Inheritance
@DiscriminatorValue("Heal")
@Getter
@Setter
public class HealItem extends Item{
  private int value;
}
