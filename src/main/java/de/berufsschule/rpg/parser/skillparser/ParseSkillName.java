package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillName extends BaseParser implements SkillParser{

  @Override
  public boolean parseSkill(ParseModel parseModel) {
    if (parseModel.getLine().contains("#NAME")) {
      Skill skill = getLastCreatedSkill(parseModel.getGamePlan());
      skill.setName(parseModel.getAndSetNextLine());
            return true;
        }
        return false;
    }
}
