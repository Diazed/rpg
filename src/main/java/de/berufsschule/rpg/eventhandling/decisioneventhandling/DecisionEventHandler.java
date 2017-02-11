package de.berufsschule.rpg.eventhandling.decisioneventhandling;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;

public interface DecisionEventHandler {

  boolean event(Decision decision, Player player, String jump, Page page);

}
