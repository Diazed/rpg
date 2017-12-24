package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Player;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Hunger implements PlayerEvent {

  @Override
  public void event(Player player) {
    Optional<Integer> optionalHunger = Optional
        .ofNullable(player.getGame().getGamePlan().getRoundHunger());
    if (optionalHunger.isPresent()) {
      increasePlayerHunger(player, optionalHunger.get());
    } else {
      increasePlayerHunger(player, 3);
    }
  }

  private void increasePlayerHunger(Player player, Integer hunger) {
    Integer newHunger = player.getHunger() + hunger;
    if (newHunger > 100) {
      player.setAlive(false);
    } else if (newHunger <= 0) {
      player.setHunger(0);
    } else {
      player.setHunger(player.getHunger() + hunger);
    }
  }
}
