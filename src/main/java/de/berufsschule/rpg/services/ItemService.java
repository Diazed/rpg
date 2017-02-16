package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.Itemeventhandling.ItemHandler;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {

  private List<ItemHandler> itemHandlers;
  private PlayerService playerService;
  private GamePlanService gamePlanService;

  @Autowired
  ItemService(List<ItemHandler> itemHandlers, PlayerService playerService, GamePlanService gamePlanService) {
    this.itemHandlers = itemHandlers;
    this.playerService = playerService;
    this.gamePlanService = gamePlanService;
  }

  public void itemEffects(Item usedItem, Player player){

    for (ItemHandler itemHandler : itemHandlers) {
      if (itemHandler.effect(usedItem, player))
        break;
    }
  }

  public Item getItem(String usedItem, Player player) {
    HashMap<String, Item> items = gamePlanService.getItemHashMapOfGamePlan(player.getGame().getName());
    return items.get(usedItem);
  }

  public List<Item> createInventory(Player player) {
    List<Item> result = new ArrayList<>();
    for (String item : player.getItems()) {

      for (Item resultItem : result) {
        if (Objects.equals(resultItem.getName(), item)) {
          resultItem.setAmount(resultItem.getAmount() + 1);
          break;
        }
        result.add(getItem(item, player));
      }


    }
    return result;
  }

  public void useItem(String usedItem, Player player) {

    Item item = getItem(usedItem, player);
    playerService.removeItemFromPlayer(player, item);
    if (item.isConsumable())
      itemEffects(item, player);
    playerService.editPlayer(player);
  }
}
