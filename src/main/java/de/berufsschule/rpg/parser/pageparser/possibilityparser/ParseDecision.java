package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.DecisionService;
import org.springframework.stereotype.Component;

@Component
public class ParseDecision extends BaseParser implements DecisionParser {

  private DecisionService decisionService;

  public ParseDecision(DecisionService decisionService) {
    this.decisionService = decisionService;
  }

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.DECISION)) {

      Decision lastCreatedDecision = getLastCreatedDecision(parseModel.getGamePlan());
      if (lastCreatedDecision != null) {
        decisionService.saveDecision(lastCreatedDecision);
      }

      Decision decision = new Decision();
      decision.setIsQuestion(false);
      getLastCreatedPage(parseModel.getGamePlan()).getDecisions().add(decision);
      return true;
    }
    return false;
  }
}
