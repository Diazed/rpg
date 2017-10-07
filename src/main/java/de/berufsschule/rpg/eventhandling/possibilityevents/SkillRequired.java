package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.services.SkillService;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillRequired implements PossibilityEvent {

  private SkillService skillService;

  @Autowired
  public SkillRequired(SkillService skillService) {
    this.skillService = skillService;
  }

  @Override
  public boolean event(Possibility possibility, Player player, Page page) {

    if (possibility.getRequiredSkill() != null && possibility.getAlternativeJump() != null
        && possibility.getSkillSuccessLvl() != null && possibility.getProbability() == null) {

      Skill skill = skillService.getSkillByName(possibility.getRequiredSkill());
      Integer successLvl = possibility.getSkillSuccessLvl();
      String jump = possibility.getJump();

      if (skill.getLevel() < successLvl && (possibility.getSkillMinLvl() == null
          || skill.getLevel() < possibility.getSkillMinLvl())) {
        player.setPosition(possibility.getAlternativeJump());
      } else if (possibility.getSkillMinLvl() != null) {

        Integer minLvl = possibility.getSkillMinLvl();
        Double steps = successLvl.doubleValue() - minLvl.doubleValue();
        Double percentIncrease = 100 / steps;
        Double chance = (skill.getLevel() - minLvl) * percentIncrease;
        int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);

        if (random > chance.intValue()) {
          player.setPosition(possibility.getAlternativeJump());
        } else {
          player.setPosition(jump);
        }
      }
      return true;
    }
    return false;
  }
}
