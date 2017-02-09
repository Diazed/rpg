package de.berufsschule.rpg.item;

import de.berufsschule.rpg.model.HealItem;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class HealItemHandler implements ItemHandler{
  @Override
  public boolean effect(Item item, Player player) {

    if (item.getClass() != HealItem.class)
      return false;

    Integer playerHealth = player.getHitpoints();
    Integer itemHealing = ((HealItem)item).getValue();
    if (playerHealth + itemHealing > 100){
      player.setHitpoints(100);
    } else {
      player.setHitpoints(playerHealth + itemHealing);
    }
    return true;
  }
}
