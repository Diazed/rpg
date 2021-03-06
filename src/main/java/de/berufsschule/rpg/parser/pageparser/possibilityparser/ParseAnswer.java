package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseAnswer extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {

    if (checkCommand(parseModel, Command.ANSWER)) {

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());

      if (optionalNextLine.isPresent()){
        if (decision.getIsQuestion()) {
          decision.setAnswer(optionalNextLine.get());
        }
      }
      return true;
    }
    return false;
  }
}
