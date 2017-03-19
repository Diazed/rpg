package de.berufsschule.rpg.eventhandling.playerevents;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Thirst implements PlayerEvent {

  @Override
  public void event(Player player, Game game) {

    Optional<Integer> optionalThirst = Optional.ofNullable(game.getRoundThirst());
    if (optionalThirst.isPresent()) {
      increasePlayerThirst(player, optionalThirst.get());
    } else {
      increasePlayerThirst(player, 5);
    }
  }

  private void increasePlayerThirst(Player player, Integer thirst) {
    if (player.getThirst() + thirst > 100) {
      player.setAlive(false);
    } else {
      player.setThirst(player.getThirst() + thirst);
    }
  }

}
