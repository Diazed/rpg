package de.berufsschule.rpg.eventhandling.Itemeventhandling;

import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;

public interface ItemHandler {

  public boolean effect(Item item, Player player);

}