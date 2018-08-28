package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillDescription extends BaseParser implements SkillParser {

  @Override
  public boolean parseSkill(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.DESCRIPTION)) {

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      Skill skill = getLastCreatedSkill(parseModel.getGamePlan());
      optionalNextLine.ifPresent(skill::setDescribtion);
      return true;
    }
    return false;
  }
}
