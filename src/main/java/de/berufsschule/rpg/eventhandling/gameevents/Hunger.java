package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Player;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Hunger implements PlayerEvent {

  @Override
  public void event(Player player) {
    Optional<Integer> optionalHunger = Optional.ofNullable(player.getGame().getRoundHunger());
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
