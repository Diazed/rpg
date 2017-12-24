package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemUse implements PossibilityEvent {

  private ItemService itemService;

  @Autowired
  public ItemUse(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean event(Possibility possibility, Player player,
      Page page) {
    if (possibility.getUsedItem() != null) {
      String usedItem = possibility.getUsedItem();
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
