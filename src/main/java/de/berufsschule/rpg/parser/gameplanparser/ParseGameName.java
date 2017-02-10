package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseGameName extends BaseParser implements GamePlanParser {

  @Override
  public boolean parseGamePlan(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#GAMENAME")){
      line = getNextLine(fileIn);
      gamePlan.setName(line);
      return true;
    }
    return false;
  }
}
