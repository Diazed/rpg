package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class XpManipulation implements PageEvent {

  @Override
  public void event(Page page, Player player) {
    if (page.getXpManipulation() != null) {
      player.setExp(player.getExp() + page.getXpManipulation());
    }
  }
}
