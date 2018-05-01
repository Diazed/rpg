package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillSuccessLevel extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (parseModel.getLine().contains("#SKILLSUCCESSLVL")) {
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());
      decision.setSkillSuccessLvl(parseInt(parseModel.getNextLine()));
      return true;
    }
    return false;
  }
}
