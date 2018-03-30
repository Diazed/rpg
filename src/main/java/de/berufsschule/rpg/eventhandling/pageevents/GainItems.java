package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class GainItems implements PageEvent {
  @Override
  public void event(Page page, Player player) {
    if (page.getItems() != null) {
      for (Item item : page.getItems()) {
        player.getItems().add(item);
      }
    }
  }
}
