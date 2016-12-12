package de.berufsschule.rpg.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{
  public Item findByName(String name);
}
