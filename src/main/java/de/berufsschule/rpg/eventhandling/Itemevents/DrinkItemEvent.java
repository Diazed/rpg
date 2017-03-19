package de.berufsschule.rpg.eventhandling.Itemevents;

import de.berufsschule.rpg.model.DrinkItem;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class DrinkItemEvent implements ItemEvent {
  @Override
  public boolean event(Item item, Player player) {

    if (item.getClass() != de.berufsschule.rpg.model.DrinkItem.class)
      return false;

    Integer playerThirst = player.getThirst();
    Integer itemValue = ((de.berufsschule.rpg.model.DrinkItem) item).getValue();
    if (playerThirst - itemValue < 0) {
      player.setThirst(0);
    } else {
      player.setThirst(playerThirst - itemValue);
    }

    return true;
  }
}
