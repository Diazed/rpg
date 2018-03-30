package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseSkill extends BaseParser implements SkillParser {

  private SkillService skillService;

  @Autowired
  public ParseSkill(SkillService skillService) {
    this.skillService = skillService;
  }

  @Override
  public boolean parseSkill(ParseModel parseModel) {
    if (parseModel.getLine().contains("#SKILL")) {
      Skill lastCreatedSkill = getLastCreatedSkill(parseModel.getGamePlan());
      if (lastCreatedSkill != null) {
        skillService.saveSkill(lastCreatedSkill);
      }

      parseModel.getGamePlan().getSkills().add(new Skill());
      return true;
    }
    return false;
  }
}
