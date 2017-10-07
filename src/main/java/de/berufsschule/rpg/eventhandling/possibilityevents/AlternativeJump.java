package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class AlternativeJump implements PossibilityEvent {

  @Override
  public boolean event(Possibility possibility, Player player, Page page) {
    if (possibility.getAlternativeJump() != null && possibility.getProbability() != null
        && possibility.getProbability() <= 100) {

      int probability = possibility.getProbability();

      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
      if (random > probability) {
        player.setPosition(possibility.getAlternativeJump());
      } else {
        player.setPosition(possibility.getJump());
      }

      return true;
    }
    return false;
  }
}
