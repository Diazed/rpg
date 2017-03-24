package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Hunger implements PlayerEvent {

  @Override
  public void event(Player player, Game game) {
    Optional<Integer> optionalHunger = Optional.ofNullable(game.getRoundHunger());
    if (optionalHunger.isPresent()) {
      increasePlayerHunger(player, optionalHunger.get());
    } else {
      increasePlayerHunger(player, 3);
    }
  }

  private void increasePlayerHunger(Player player, Integer hunger) {
    if (player.getHunger() + hunger > 100) {
      player.setAlive(false);
    } else {
      player.setHunger(player.getHunger() + hunger);
    }
  }
}
