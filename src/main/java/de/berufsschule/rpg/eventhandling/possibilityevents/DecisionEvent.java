package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;

public interface DecisionEvent {

  boolean event(Decision decision, Player player, Page page);

}
