package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.decisionevents.DecisionEvent;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DecisionService {

  private List<DecisionEvent> decisionEvents;

  @Autowired
  public DecisionService(List<DecisionEvent> decisionEvents) {
    this.decisionEvents = decisionEvents;
  }


  public void runDecisionEvents(Decision decision, Player player, String jump, Page page) {
    for (DecisionEvent decisionEvent : decisionEvents) {
      decisionEvent.event(decision, player, jump, page);
    }
  }


  public Decision getClickedDecision(Page currentPlayerPage, String jump) {

    for (Decision decision : currentPlayerPage.getDecisions()) {
      if (decision.getJump().equals(jump))
        return decision;
    }
    return null;
  }


}
