package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemUse implements DecisionEvent {

  private ItemService itemService;

  @Autowired
  public ItemUse(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean event(Decision decision, Player player, Page page) {
    if (decision.getUsedItem() != null) {
      String usedItem = decision.getUsedItem();
      for (String item : player.getItems()) {
        if (item.equals(usedItem)) {
          itemService.useItem(usedItem, player);
          page.setUsedItem(usedItem);
          return true;
        }
      }
    }
    return false;
  }
}
