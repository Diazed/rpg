package de.berufsschule.rpg.eventhandling.itemevents;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class FoodItemEvent implements ItemEvent {
  @Override
  public boolean event(Item item, Player player) {

    if (item.getClass() != de.berufsschule.rpg.domain.model.FoodItem.class)
      return false;

    Integer playerHunger = player.getHunger();
    Integer itemValue = ((de.berufsschule.rpg.domain.model.FoodItem) item).getValue();
    if (playerHunger - itemValue < 0) {
      player.setHunger(0);
    } else {
      player.setHunger(playerHunger - itemValue);
    }
    return true;
  }
}
