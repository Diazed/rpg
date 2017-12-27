package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseRoundThirst extends BaseParser implements GamePlanParser {

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (parseModel.getLine().contains("#ROUNDTHIRST")) {
      Integer roundThirst = parseInt(parseModel.getAndSetNextLine());
      parseModel.getGamePlan().setRoundThirst(roundThirst);
      return true;
    }
    return false;
  }
}
