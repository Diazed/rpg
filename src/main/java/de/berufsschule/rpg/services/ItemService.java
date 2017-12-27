package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.itemevents.ItemEvent;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.repositories.ItemRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  private List<ItemEvent> itemEvents;
  private PlayerService playerService;
  private ItemRepository itemRepository;

  @Autowired
  ItemService(List<ItemEvent> itemEvents, PlayerService playerService,
      ItemRepository itemRepository) {
    this.itemEvents = itemEvents;
    this.playerService = playerService;
    this.itemRepository = itemRepository;
  }

  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  public void itemEffects(Item usedItem, Player player) {

    for (ItemEvent itemEvent : itemEvents) {
      if (itemEvent.event(usedItem, player)) {
        break;
      }
    }
  }

  public Item getItem(Integer id) {
    return itemRepository.findOne(id);
  }

  public List<Item> createInventory(Player player) {

    boolean newItem;
    List<Item> inventory = new ArrayList<>();

    for (Item ownedItem : player.getItems()) {

      Item item = getItem(ownedItem.getId());
      newItem = isNewItem(inventory, ownedItem);
      if (newItem) {
        item.setAmount(0);
        setItemAmount(player, ownedItem, item);
        inventory.add(item);
      }
    }
    return inventory;
  }

  private void setItemAmount(Player player, Item ownedItem, Item item) {
    for (Item otherItem : player.getItems()) {
      if (otherItem.getName().equals(ownedItem.getName())) {
        int amount = item.getAmount();
        item.setAmount(amount + 1);
      }
    }
  }

  private boolean isNewItem(List<Item> inventory, Item ownedItem) {
    boolean newItem;
    newItem = true;
    for (Item invItem : inventory) {
      if (invItem.getName().equals(ownedItem.getName())) {
        newItem = false;
        break;
      }
    }
    return newItem;
  }

  public void useItem(Item usedItem, Player player) {

    playerService.removeItemFromPlayer(player, usedItem);
    itemEffects(usedItem, player);
    playerService.savePlayer(player);
  }
}
