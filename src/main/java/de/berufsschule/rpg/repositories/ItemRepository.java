package de.berufsschule.rpg.repositories;

import de.berufsschule.rpg.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{
  public List<Item> findByName(String name);
}