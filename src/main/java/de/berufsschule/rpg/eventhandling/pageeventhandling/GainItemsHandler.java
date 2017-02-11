package de.berufsschule.rpg.eventhandling.pageeventhandling;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class GainItemsHandler implements PageEventHandler {
  @Override
  public boolean event(Page page, Player player) {
    if (page.getItems() != null) {
      for (String item : page.getItems()) {
        player.getItems().add(item);
      }
      return true;
    }
    return false;
  }
}
