package de.berufsschule.rpg.eventhandling.decisioneventhandling;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class InjuryHandler implements DecisionEventHandler {
  @Override
  public boolean event(Decision decision, Player player, String jump, Page page) {
    if (decision.getInjury() > 0) {
      int injury = decision.getInjury();
      int playerHealth = player.getHitpoints();

      if (injury > 0) {
        if (playerHealth - injury < 0) {
          player.setAlive(false);
        } else {
          player.setHitpoints(playerHealth - injury);
        }
      }
      return true;
    }
    return false;
  }
}
