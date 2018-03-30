package de.berufsschule.rpg.eventhandling.itemevents;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Player;

public interface ItemEvent {

  boolean event(Item item, Player player);

}
