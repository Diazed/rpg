package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.decisionevents.DecisionEvent;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Skill;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DecisionService {

  private List<DecisionEvent> decisionEvents;

  @Autowired
  public DecisionService(List<DecisionEvent> decisionEvents) {
    this.decisionEvents = decisionEvents;
  }


  public void runDecisionEvents(Decision decision, Player player, Page page) {
    for (DecisionEvent decisionEvent : decisionEvents) {
      decisionEvent.event(decision, player, page);
    }
  }


  public Decision getClickedDecision(Page currentPlayerPage, String jump) {

    for (Decision decision : currentPlayerPage.getDecisions()) {
      if (decision.getJump().equals(jump))
        return decision;
    }
    return null;
  }

  public Decision setFlags(Decision decision, Player player) {
    decision.setHasItem(playerOwnsItem(decision, player));
    decision.setHasSkill(playerOwnsSkill(decision, player));
    return decision;
  }

  private boolean playerOwnsSkill(Decision decision, Player player) {
    String requiredSkill = decision.getRequiredSkill();
    if (requiredSkill != null) {
      for (Skill skill : player.getSkills()) {
        if (skill.getName().equals(requiredSkill) && decision.getSkillMinLvl() <= skill
            .getLevel()) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  private boolean playerOwnsItem(Decision decision, Player player) {
    String itemname = decision.getUsedItem();
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
