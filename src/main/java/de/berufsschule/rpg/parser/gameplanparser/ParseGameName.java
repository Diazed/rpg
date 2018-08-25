package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseGameName extends BaseParser implements GamePlanParser {

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.GAMENAME)) {
      parseModel.getGamePlan().setName(parseModel.getAndSetNextLine());
      return true;
    }
    return false;
  }
}
