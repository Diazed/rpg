package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;

public class ParseAltAnswer extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {

    if (checkCommand(parseModel, Command.ALTANSWER)) {

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
