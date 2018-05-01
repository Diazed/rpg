package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseUseItem extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (parseModel.getLine().contains("#USE")) {
      getLastCreatedDecision(parseModel.getGamePlan())
          .setUsedItem(parseModel.getAndSetNextLine());
      return true;
    }
    return false;
  }
}
