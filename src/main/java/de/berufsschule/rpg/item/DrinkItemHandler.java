package de.berufsschule.rpg.item;

import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class DrinkItemHandler implements ItemHandler {
  @Override
  public boolean effect(Item item, Player player) {

    if (item.getClass() != DrinkItem.class)
      return false;

    Integer playerThirst = player.getThirst();
    Integer itemValue = ((DrinkItem)item).getValue();
    if (playerThirst - itemValue < 0) {
      player.setThirst(0);
    } else {
      player.setThirst(playerThirst - itemValue);
    }

    return true;
  }
}
