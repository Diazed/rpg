package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.decisioneventhandling.DecisionEventHandler;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DecisionService {

  private List<DecisionEventHandler> decisionEventHandlers;

  @Autowired
  public DecisionService(List<DecisionEventHandler> decisionEventHandlers) {
    this.decisionEventHandlers = decisionEventHandlers;
  }


  public void runDecisionEvents(Decision decision, Player player, String jump, Page page) {
    for (DecisionEventHandler decisionEventHandler : decisionEventHandlers) {
      decisionEventHandler.event(decision, player, jump, page);
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
