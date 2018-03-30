package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseGameName extends BaseParser implements GamePlanParser {

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (parseModel.getLine().contains("#GAMENAME")) {
      parseModel.getGamePlan().setName(parseModel.getAndSetNextLine());
      return true;
    }
    return false;
  }
}
