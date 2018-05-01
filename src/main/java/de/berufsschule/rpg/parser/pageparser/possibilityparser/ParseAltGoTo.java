package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseAltGoTo extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (parseModel.getLine().contains("#ALTGOTO")) {

      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());
      String line = parseModel.getAndSetNextLine();

      if (decision.getIsQuestion()) {
        return true;
      }

      Page pageByName = findPageByName(parseModel.getGamePlan(), line);

      if (pageByName == null) {
        parseModel.getUncompleteDecisions().add(decision);
      } else {
        decision.setAltJump(pageByName.getId());
      }
      decision.setAltJumpName(line);
      return true;


    }
    return false;
  }
}
