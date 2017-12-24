package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseSkill extends BaseParser implements SkillParser {

  private SkillService skillService;

  @Autowired
  public ParseSkill(SkillService skillService) {
    this.skillService = skillService;
  }

  @Override
  public boolean parseSkill(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#SKILL")) {
      Skill lastCreatedSkill = getLastCreatedSkill(gamePlan);
      if (lastCreatedSkill != null) {
        skillService.saveSkill(lastCreatedSkill);
      }

      gamePlan.getSkills().add(new Skill());
      return true;
    }
    return false;
  }
}
