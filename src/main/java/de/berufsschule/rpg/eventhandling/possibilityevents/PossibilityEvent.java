package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Possibility;

public interface PossibilityEvent {

  boolean event(Possibility possibility, Player player, Page page);

}
