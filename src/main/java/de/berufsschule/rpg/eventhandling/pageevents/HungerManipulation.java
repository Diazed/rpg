package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class HungerManipulation implements PageEvent {
  @Override
  public void event(Page page, Player player) {
    if (page.getHungerManipulation() != null) {
      player.setHunger(player.getHunger() + page.getHungerManipulation());
    }
  }
}
