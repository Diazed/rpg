package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;

public interface PossibilityEvent {

  boolean event(Possibility possibility, Player player, Page page);

}
