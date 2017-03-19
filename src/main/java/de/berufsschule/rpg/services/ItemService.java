package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.Itemevents.ItemEvent;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ItemService {

  private List<ItemEvent> itemEvents;
  private PlayerService playerService;
  private GamePlanService gamePlanService;

  @Autowired
  ItemService(List<ItemEvent> itemEvents, PlayerService playerService,
              GamePlanService gamePlanService) {
    this.itemEvents = itemEvents;
    this.playerService = playerService;
    this.gamePlanService = gamePlanService;
  }

  public void itemEffects(Item usedItem, Player player) {

    for (ItemEvent itemEvent : itemEvents) {
      if (itemEvent.event(usedItem, player)) {
        break;
      }
    }
  }

  public Item getItem(String usedItem, Player player) {
    HashMap<String, Item> items = gamePlanService
        .getItemHashMapOfGamePlan(player.getGame().getName());
    return items.get(usedItem);
  }

  public List<Item> createInventory(Player player) {

    boolean newItem;
    List<Item> inventory = new ArrayList<>();

    for (String ownedItem : player.getItems()) {

      Item item = getItem(ownedItem, player);
      newItem = isNewItem(inventory, ownedItem);
      if (newItem) {
        item.setAmount(0);
        setItemAmount(player, ownedItem, item);
        inventory.add(item);
      }
    }
    return inventory;
  }

  private void setItemAmount(Player player, String ownedItem, Item item) {
    for (String otherItem : player.getItems()) {

      if (otherItem.equals(ownedItem)) {
        int amount = item.getAmount();
        item.setAmount(amount + 1);
      }
    }
  }

  private boolean isNewItem(List<Item> inventory, String ownedItem) {
    boolean newItem;
    newItem = true;
    for (Item invItem : inventory) {
      if (invItem.getName().equals(ownedItem)) {
        newItem = false;
        break;
      }
    }
    return newItem;
  }

  public void useItem(String usedItem, Player player) {

    Item item = getItem(usedItem, player);
    playerService.removeItemFromPlayer(player, item);
    itemEffects(item, player);
    playerService.editPlayer(player);
  }
}
