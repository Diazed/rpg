package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseUseItem extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#USE")) {
      line = getNextLine(fileIn);
      getLastCreatedDecision(gamePlan).setUsedItem(line);
      return true;
    }
    return false;
  }
}
