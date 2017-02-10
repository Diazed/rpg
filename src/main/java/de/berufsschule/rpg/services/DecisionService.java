package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class DecisionService {

  public Page prepareDecisions(Page page, Player player) {

    for (Decision decision : page.getDecisions()) {
      String neededItem = decision.getNeededItem();
      if (neededItem != null) {
        for (String item : player.getItems()) {
          if (item.equals(neededItem)) {
            decision.setHasItem(true);
            break;
          }
        }
      }
    }

    return page;
  }

  public boolean isFailPossible(Decision clickedDecision){
    return clickedDecision.getProbability() != 0 && clickedDecision.getAlternativeJump() != null;
  }

  public void decisionFailOrSuccess(int probability, String failJump, String jump, Player player) {

    if (probability >= 100) {
      player.setPosition(jump);
      return;
    }
    int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
    if (random > probability){
      player.setPosition(failJump);
    }else {
      player.setPosition(jump);
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
