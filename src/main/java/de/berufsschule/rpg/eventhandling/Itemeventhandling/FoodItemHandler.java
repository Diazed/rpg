package de.berufsschule.rpg.eventhandling.Itemeventhandling;

import de.berufsschule.rpg.model.FoodItem;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class FoodItemHandler implements ItemHandler {
  @Override
  public boolean effect(Item item, Player player) {

    if (item.getClass() != FoodItem.class)
      return false;

    Integer playerHunger = player.getHunger();
    Integer itemValue = ((FoodItem)item).getValue();
    if (playerHunger - itemValue < 0) {
      player.setHunger(0);
    } else {
      player.setHunger(playerHunger - itemValue);
    }
    return true;
  }
}
