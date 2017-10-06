package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class AlternativeJump implements DecisionEvent {
  @Override
  public boolean event(Decision decision, Player player, Page page) {
    if (decision.getAlternativeJump() != null && decision.getProbability() != null
        && decision.getProbability() <= 100) {

      int probability = decision.getProbability();

      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
      if (random > probability) {
        player.setPosition(decision.getAlternativeJump());
      } else {
        player.setPosition(decision.getJump());
      }

      return true;
    }
    return false;
  }
}
