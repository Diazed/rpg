package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.DecisionService;
import org.springframework.stereotype.Component;

@Component
public class ParseQuestion extends BaseParser implements DecisionParser {

  private DecisionService decisionService;

  public ParseQuestion(DecisionService decisionService) {
    this.decisionService = decisionService;
  }

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.QUESTION)) {

      Decision lastCreatedPossibility = getLastCreatedDecision(parseModel.getGamePlan());
      if (lastCreatedPossibility != null) {
        decisionService.saveDecision(lastCreatedPossibility);
      }

      Decision decision = new Decision();
      decision.setIsQuestion(true);
      getLastCreatedPage(parseModel.getGamePlan()).getDecisions().add(decision);
      return true;
    }
    return false;
  }
}
