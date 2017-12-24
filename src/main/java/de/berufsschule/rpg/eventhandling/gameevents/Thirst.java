package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Player;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Thirst implements PlayerEvent {

  @Override
  public void event(Player player) {

    Optional<Integer> optionalThirst = Optional
        .ofNullable(player.getGame().getGamePlan().getRoundThirst());
    if (optionalThirst.isPresent()) {
      increasePlayerThirst(player, optionalThirst.get());
    } else {
      increasePlayerThirst(player, 5);
    }
  }

  private void increasePlayerThirst(Player player, Integer thirst) {
    Integer newThirst = player.getThirst() + thirst;
    if (newThirst > 100) {
      player.setAlive(false);
    } else if (newThirst <= 0) {
      player.setThirst(0);
    } else {
      player.setThirst(player.getThirst() + thirst);
    }
  }
}
