package de.berufsschule.rpg.eventhandling.Itemevents;

import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;

public interface ItemEvent {

  public boolean event(Item item, Player player);

}
