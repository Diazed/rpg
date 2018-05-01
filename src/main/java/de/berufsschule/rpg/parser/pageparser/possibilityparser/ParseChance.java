package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseChance extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (parseModel.getLine().contains("#CHANCE")) {
      int probability = parseInt(parseModel.getAndSetNextLine());
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());
      decision.setProbability(probability);
      return true;
    }
    return false;
  }
}
