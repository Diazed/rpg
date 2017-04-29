package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class AlternativeJump implements DecisionEvent {
  @Override
  public boolean event(Decision decision, Player player, String jump, Page page) {
    if (decision.getAlternativeJump() != null && decision.getProbability() != 0) {

      int probability = decision.getProbability();
      String failJump = decision.getAlternativeJump();

      if (probability >= 100) {
        player.setPosition(jump);
        return true;
      }
      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
      if (random > probability) {
        player.setPosition(failJump);
      } else {
        player.setPosition(jump);
      }

      return true;
    }
    return false;
  }
}
