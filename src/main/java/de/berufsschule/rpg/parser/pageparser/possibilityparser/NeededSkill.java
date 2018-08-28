package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class NeededSkill extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.REQUIREDSKILL)) {
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      if (optionalNextLine.isPresent()){
        Skill skillByName = findSkillByName(parseModel.getGamePlan(), optionalNextLine.get());
        decision.setRequiredSkillId(skillByName.getId());
        decision.setRequiredSkill(skillByName.getName());
      }

      return true;
    }
    return false;
  }
}
