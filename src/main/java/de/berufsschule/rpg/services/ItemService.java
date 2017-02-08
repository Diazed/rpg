package de.berufsschule.rpg.services;

import de.berufsschule.rpg.item.ItemHandler;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

  ItemRepository itemRepository;
  List<ItemHandler> itemHandlers;

  @Autowired
  ItemService(ItemRepository itemRepository, List<ItemHandler> itemHandlers){
    this.itemRepository = itemRepository;
    this.itemHandlers = itemHandlers;
  }

  public void itemEffects(Item usedItem, Player player){
    for(ItemHandler aItemHandler : itemHandlers){
      if (aItemHandler.effect(usedItem, player))
        break;
    }
  }

  public void saveNewItem(Item item){
    itemRepository.save(item);
  }

  public Item findItemById(Integer id) {
    return itemRepository.findOne(id);
  }



  public Item findItemByNameAndPlayerId(String itemName, Integer playerId){
    List<Item> items;
    items = itemRepository.findByName(itemName);
    Integer itemSize = items.size();

    for (int i=0; i<itemSize; i++){
      if (items.get(i).getPlayerId() == playerId){
        return items.get(i);
      }
    }

    return null;
  }

  public void editItemAmount(Item editedItem){
    Item originalItem = itemRepository.findOne(editedItem.getId());

    originalItem.setAmount(editedItem.getAmount());

    itemRepository.save(originalItem);
  }

  public void deleteItem(Integer id){
    itemRepository.delete(id);
  }

}
