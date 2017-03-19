package de.berufsschule.rpg.eventhandling.Itemevents;

import de.berufsschule.rpg.model.HealItem;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class HealItemEvent implements ItemEvent {
  @Override
  public boolean event(Item item, Player player) {

    if (item.getClass() != de.berufsschule.rpg.model.HealItem.class)
      return false;

    Integer playerHealth = player.getHitpoints();
    Integer itemHealing = ((de.berufsschule.rpg.model.HealItem) item).getValue();
    if (playerHealth + itemHealing > 100){
      player.setHitpoints(100);
    } else {
      player.setHitpoints(playerHealth + itemHealing);
    }
    return true;
  }
}
