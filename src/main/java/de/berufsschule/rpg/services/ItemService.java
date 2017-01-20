package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

  ItemRepository itemRepository;

  @Autowired
  ItemService(ItemRepository itemRepository){
    this.itemRepository = itemRepository;
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

  public void editItem(Item editedItem){
    Item originalItem = itemRepository.findOne(editedItem.getId());

    originalItem.setAmount(editedItem.getAmount());
    originalItem.setName(editedItem.getName());
    originalItem.setConsumable(editedItem.isConsumable());
    originalItem.setDescription(editedItem.getDescription());
    originalItem.setDrink(editedItem.isDrink());
    originalItem.setValue(editedItem.getValue());

    itemRepository.save(originalItem);
  }

  public void deleteItem(Integer id){
    itemRepository.delete(id);
  }

}
