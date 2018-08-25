package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseRoundExp extends BaseParser implements GamePlanParser {

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.ROUNDEXP)) {
      Integer roundExp = parseInt(parseModel.getAndSetNextLine());
      parseModel.getGamePlan().setRoundExp(roundExp);
      return true;
    }
    return false;
  }
}
