package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.possibilityevents.PossibilityEvent;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Skill;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PossibilityService {

  private List<PossibilityEvent> possibilityEvents;

  @Autowired
  public PossibilityService(List<PossibilityEvent> possibilityEvents) {
    this.possibilityEvents = possibilityEvents;
  }


  public void runDecisionEvents(Possibility possibility, Player player, Page page) {
    for (PossibilityEvent possibilityEvent : possibilityEvents) {
      possibilityEvent.event(possibility, player, page);
    }
  }


  public Possibility getClickedDecision(Page currentPlayerPage, String jump) {

    for (Possibility possibility : currentPlayerPage.getPossibilities()) {
      if (possibility.getJump().equals(jump)) {
        return possibility;
      }
    }
    return null;
  }

  public Possibility setFlags(Possibility possibility, Player player) {
    possibility.setHasItem(playerOwnsItem(possibility, player));
    possibility.setHasSkill(playerOwnsSkill(possibility, player));
    return possibility;
  }

  private boolean playerOwnsSkill(Possibility possibility, Player player) {
    String requiredSkill = possibility.getRequiredSkill();
    if (requiredSkill != null) {
      for (Skill skill : player.getSkills()) {
        if (skill.getName().equals(requiredSkill) && possibility.getSkillMinLvl() <= skill
            .getLevel()) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  private boolean playerOwnsItem(Possibility possibility, Player player) {
    String itemname = possibility.getUsedItem();
    if (itemname != null) {
      for (String item : player.getItems()) {
        if (item.equals(itemname)) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

}
