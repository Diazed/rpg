package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.Itemeventhandling.ItemHandler;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

  private List<ItemHandler> itemHandlers;
  private PlayerService playerService;

  @Autowired
  ItemService(List<ItemHandler> itemHandlers, PlayerService playerService) {
    this.itemHandlers = itemHandlers;
    this.playerService = playerService;
  }

  public void itemEffects(Item usedItem, Player player){

    for (ItemHandler itemHandler : itemHandlers) {
      if (itemHandler.effect(usedItem, player))
        break;
    }
  }

  public Item getItem(String usedItem, Player player) {
    return player.getGame().getItems().get(usedItem);
  }

  public void useItem(String usedItem, Player player) {

    Item item = getItem(usedItem, player);
    playerService.removeItemFromPlayer(player, item);
    if (item.isConsumable())
      itemEffects(item, player);
    playerService.editPlayer(player);
  }
}
