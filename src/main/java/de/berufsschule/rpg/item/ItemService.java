package de.berufsschule.rpg.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Item findItemByName(String name){
    return itemRepository.findByName(name);
  }

}
