package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseAnswer extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {

    if (parseModel.checkCompetence("#ANSWER")) {

      String line = parseModel.getAndSetNextLine();
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());

      if (decision.getIsQuestion()) {
        decision.setAnswer(line);
      }
      return true;
    }

    return false;
  }
}
