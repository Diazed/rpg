package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class Checkpoint implements PageEvent {
  @Override
  public void event(Page currentPage, Player player) {
    if (currentPage.isCheckpoint()) {
      player.setCheckpoint(currentPage.getID());
    }
  }
}
