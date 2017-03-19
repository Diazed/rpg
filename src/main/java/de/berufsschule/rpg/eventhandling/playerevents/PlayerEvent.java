package de.berufsschule.rpg.eventhandling.playerevents;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;

public interface PlayerEvent {

  void event(Player player, Game game);

}
