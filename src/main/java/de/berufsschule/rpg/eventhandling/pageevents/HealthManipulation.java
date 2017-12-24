package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class HealthManipulation implements PageEvent {
  @Override
  public void event(Page page, Player player) {
    if (page.getHealthManipulation() != null) {
      Integer newHealth = player.getHitpoints() + page.getHealthManipulation();
      if (newHealth >= 100) {
        player.setHitpoints(100);
      } else {
        player.setHitpoints(newHealth);
      }
    }
  }
}
