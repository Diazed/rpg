package de.berufsschule.rpg.eventhandling.pageeventhandling;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class CheckpointHandler implements PageEventHandler {
  @Override
  public boolean event(Page currentPage, Player player) {
    if (currentPage.isCheckpoint()) {
      player.setCheckpoint(currentPage.getName());
      return true;
    }
    return false;
  }
}
