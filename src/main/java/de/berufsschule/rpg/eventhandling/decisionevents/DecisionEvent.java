package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;

public interface DecisionEvent {

  boolean event(Decision decision, Player player, String jump, Page page);

}
