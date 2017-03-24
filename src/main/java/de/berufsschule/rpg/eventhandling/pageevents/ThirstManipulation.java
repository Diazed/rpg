package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class ThirstManipulation implements PageEvent {
  @Override
  public void event(Page page, Player player) {
    if (page.getThirstManipulation() != null) {
      player.setThirst(player.getThirst() + page.getThirstManipulation());
    }
  }
}
