package de.berufsschule.rpg.eventhandling.pageevents;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class SkillPointManipulation implements PageEvent {

  @Override
  public void event(Page page, Player player) {
    if (page.getSkillPointManipulation() != null) {
      Integer playerSkillPoints = player.getSkillPoints();
      Integer skillPointManipulation = page.getSkillPointManipulation();
      player.setSkillPoints(playerSkillPoints + skillPointManipulation);
    }
  }
}
