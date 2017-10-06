package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.services.SkillService;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillRequired implements DecisionEvent {

  private SkillService skillService;

  @Autowired
  public SkillRequired(SkillService skillService) {
    this.skillService = skillService;
  }

  @Override
  public boolean event(Decision decision, Player player, Page page) {

    if (decision.getRequiredSkill() != null && decision.getAlternativeJump() != null
        && decision.getSkillSuccessLvl() != null && decision.getProbability() == null) {

      Skill skill = skillService.getSkillByName(decision.getRequiredSkill());
      Integer successLvl = decision.getSkillSuccessLvl();
      String jump = decision.getJump();

      if (skill.getLevel() < successLvl && (decision.getSkillMinLvl() == null
          || skill.getLevel() < decision.getSkillMinLvl())) {
        player.setPosition(decision.getAlternativeJump());
      } else if (decision.getSkillMinLvl() != null) {

        Integer minLvl = decision.getSkillMinLvl();
        Double steps = successLvl.doubleValue() - minLvl.doubleValue();
        Double percentIncrease = 100 / steps;
        Double chance = (skill.getLevel() - minLvl) * percentIncrease;
        int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);

        if (random > chance.intValue()) {
          player.setPosition(decision.getAlternativeJump());
        } else {
          player.setPosition(jump);
        }
      }
      return true;
    }
    return false;
  }
}
