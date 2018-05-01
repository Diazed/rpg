package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Skill;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SkillRequired implements DecisionEvent {

  @Override
  public boolean event(Decision decision, Player player, Page page) {

    if (decision.getRequiredSkill() != null
        && decision.getSkillSuccessLvl() != null
        && decision.getProbability() == null) {

      Optional<Skill> optionalSkill = searchForSkill(decision.getRequiredSkillId(),
          player.getSkills());

      if (!optionalSkill.isPresent()) {
        log.debug("Player does not own required Skill.");
        return false;
      }
      Skill skill = optionalSkill.get();

      Integer successLvl = decision.getSkillSuccessLvl();

      Integer minLvl;
      if (decision.getSkillMinLvl() == null) {
        minLvl = 0;
      } else {
        minLvl = decision.getSkillMinLvl();
      }

      Double steps = successLvl.doubleValue() - minLvl.doubleValue();
      Double percentIncrease = 100 / steps;
      Double chance = (skill.getLevel() - minLvl) * percentIncrease;
      int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);

      boolean takeAlt = random > chance.intValue();

      if (decision.getAltJump() == null) {
        return false;
      }
      if (takeAlt) {
        player.setPosition(decision.getAltJump());
      } else {
        player.setPosition(decision.getMainJump());
      }

      return true;
    }
    return false;
  }

  private Optional<Skill> searchForSkill(Integer requiredSkillId, List<Skill> playerSkills) {

    for (Skill playerSkill : playerSkills) {
      if (playerSkill.getId().equals(requiredSkillId)) {
        return Optional.of(playerSkill);
      }
    }
    return Optional.empty();
  }

}
