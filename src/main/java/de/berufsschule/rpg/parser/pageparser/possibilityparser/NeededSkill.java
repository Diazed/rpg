package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class NeededSkill extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (parseModel.getLine().contains("#REQUIREDSKILL")) {
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());
      Skill skillByName = findSkillByName(parseModel.getGamePlan(), parseModel.getAndSetNextLine());
      decision.setRequiredSkillId(skillByName.getId());
      decision.setRequiredSkill(skillByName.getName());

      return true;
    }
    return false;
  }
}
