package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Question;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class AlternativeJump implements PossibilityEvent {

  @Override
  public boolean event(Possibility possibility, Player player, Page page) {
    if (possibility.getProbability() != null && possibility.getProbability() <= 100) {

      boolean takeAlt = false;

      int probability = possibility.getProbability();

      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
      if (random > probability) {
        takeAlt = true;
      }

      if (possibility.getClass() == Question.class) {
        Question question = (Question) possibility;
        if (question.getAltAnswer() == null) {
          return false;
        }
        question.setTakeAlt(takeAlt);
      } else {
        Decision decision = (Decision) possibility;
        if (decision.getAltJump() == null) {
          return false;
        }
        if (takeAlt) {
          player.setPosition(decision.getAltJump());
        } else {
          player.setPosition(decision.getMainJump());
        }
      }

      return true;
    }
    return false;
  }
}
