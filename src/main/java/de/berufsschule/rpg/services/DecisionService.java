package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DecisionService {

  public Page prepareDecisions(Page page, Player player) {
    Integer pageDecisions = page.getDecisions().size();
    Integer playerItems = player.getItems().size();
    for (int i = 0; i < pageDecisions; i++) {
      if (!Objects.equals(page.getDecisions().get(i).getNeededItem(), null)) {
        for (int j = 0; j < playerItems; j++) {
          if (Objects.equals(page.getDecisions().get(i).getNeededItem(), player.getItems().get(j).getName())) {
            page.getDecisions().get(i).setHasItem(true);
          }
        }
      }
    }
    return page;
  }

  public boolean isFailPossible(Decision clickedDecision){
    if (clickedDecision.getProbability() != 0 && clickedDecision.getAlternativeJump() != null){
      return true;
    }
    return false;
  }

  public void decisionFailOrSuccess(int probability, String failJump,String gameName, String jump, Player player){

    if (probability >= 100)
      player.getPosition().put(gameName, jump);
    int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
    if (random > probability){
      player.getPosition().put(gameName, failJump);
    }else {
      player.getPosition().put(gameName, jump);
    }
  }

  public Decision getClickedDecision(Page currentPlayerPage, String jump) {
    Decision jumpDecision = new Decision();
    for (int i = 0; i < currentPlayerPage.getDecisions().size(); i++) {
      if (isJumpInPlayerPage(currentPlayerPage.getDecisions().get(i), jump))
        jumpDecision = currentPlayerPage.getDecisions().get(i);
    }
    return jumpDecision;
  }

  private boolean isJumpInPlayerPage(Decision decision, String jump) {
    return Objects.equals(decision.getJump(), jump);
  }

}
