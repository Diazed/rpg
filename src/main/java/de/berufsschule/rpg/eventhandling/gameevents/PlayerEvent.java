package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.domain.model.Player;

public interface PlayerEvent {

  void event(Player player);

}
