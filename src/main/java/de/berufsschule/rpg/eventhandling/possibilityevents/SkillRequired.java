package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Possibility;
import de.berufsschule.rpg.domain.model.Question;
import de.berufsschule.rpg.domain.model.Skill;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class SkillRequired implements PossibilityEvent {

  @Override
  public boolean event(Possibility possibility, Player player, Page page) {

    if (possibility.getRequiredSkill() != null && possibility.getSkillSuccessLvl() != null
        && possibility.getProbability() == null) {

      Skill skill = null;

      for (Skill playerSkill : player.getSkills()) {
        if (playerSkill.getName().equals(possibility.getRequiredSkill())) {
          skill = playerSkill;
        }
      }

      Integer successLvl = possibility.getSkillSuccessLvl();
      boolean takeAlt = false;

      Integer minLvl = possibility.getSkillMinLvl();
      Double steps = successLvl.doubleValue() - minLvl.doubleValue();
      Double percentIncrease = 100 / steps;
      Double chance = (skill.getLevel() - minLvl) * percentIncrease;
      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);

      takeAlt = random > chance.intValue();

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
