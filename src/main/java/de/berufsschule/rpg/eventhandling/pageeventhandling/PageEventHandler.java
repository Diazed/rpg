package de.berufsschule.rpg.eventhandling.pageeventhandling;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;

public interface PageEventHandler {

  boolean event(Page page, Player player);

}
