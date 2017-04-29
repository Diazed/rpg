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
  public boolean event(Decision decision, Player player, String jump, Page page) {

    if (decision.getRequiredSkill() != null && decision.getAlternativeJump() != null) {

      Skill skill = skillService.getSkillByName(decision.getRequiredSkill());

      Integer successLvl = decision.getSkillSuccessLvl();

      if (skill.getLevel() >= successLvl) {
        player.setPosition(jump);
        return true;
      }

      Integer minLvl = decision.getSkillMinLvl() - 1;
      Integer steps = successLvl - minLvl;
      Integer percentIncrease = 100 / steps;
      Integer chance = skill.getLevel() * percentIncrease;
      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);

      if (random > chance) {
        player.setPosition(decision.getAlternativeJump());
      } else {
        player.setPosition(jump);
      }
      return true;
    }
    return false;
  }
}
