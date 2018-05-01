package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
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
  public boolean event(Decision decision, Player player,
      Page page) {

    if (decision.getUsedItem() != null) {
      String usedItem = decision.getUsedItem();
      for (Item item : player.getItems()) {
        if (item.getName().equals(usedItem)) {
          itemService.useItem(item, player);
          page.setUsedItem(usedItem);
          return true;
        }
      }
    }
    return false;
  }
}
