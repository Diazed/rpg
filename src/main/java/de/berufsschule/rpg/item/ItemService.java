package de.berufsschule.rpg.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

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

  public Item findItemByName(String name){
    return itemRepository.findByName(name);
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
