package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillName extends BaseParser implements SkillParser {

  @Override
  public boolean parseSkill(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.NAME)) {

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      Skill skill = getLastCreatedSkill(parseModel.getGamePlan());
      optionalNextLine.ifPresent(skill::setName);
      return true;
    }
    return false;
  }
}
