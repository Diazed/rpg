package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class AlternativeJump implements DecisionEvent {

  @Override
  public boolean event(Decision decision, Player player, Page page) {
    if (decision.getProbability() != null && decision.getProbability() <= 100) {

      boolean takeAlt = false;

      int probability = decision.getProbability();

      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
      if (random > probability) {
        takeAlt = true;
      }

      if (decision.getAltJump() == null) {
        return false;
      }
      if (takeAlt) {
        player.setPosition(decision.getAltJump());
      } else {
        player.setPosition(decision.getMainJump());
      }
      return true;
    }
    return false;
  }
}
