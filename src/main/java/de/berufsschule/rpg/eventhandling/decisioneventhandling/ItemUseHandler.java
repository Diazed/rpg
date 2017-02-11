package de.berufsschule.rpg.eventhandling.decisioneventhandling;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemUseHandler implements DecisionEventHandler {

  private ItemService itemService;

  @Autowired
  public ItemUseHandler(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean event(Decision decision, Player player, String jump, Page page) {
    if (decision.getNeededItem() != null) {
      String usedItem = decision.getNeededItem();
      for (String item : player.getItems()) {
        if (item.equals(usedItem)) {
          itemService.useItem(usedItem, player);
          return true;
        }
      }
    }
    return false;
  }
}
