package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

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
