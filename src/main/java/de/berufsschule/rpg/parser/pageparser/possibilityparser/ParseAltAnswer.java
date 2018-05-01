package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;

public class ParseAltAnswer extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {

    if (parseModel.checkCompetence("#ALTANSWER")) {

      String line = parseModel.getAndSetNextLine();
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());

      if (decision.getIsQuestion()) {
        decision.setAnswer(line);
        return true;
      } else {
        return false;
      }
    }

    return false;
  }
}
