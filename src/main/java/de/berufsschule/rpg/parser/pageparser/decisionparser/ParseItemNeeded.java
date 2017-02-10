package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseItemNeeded extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ITEMNEEDED")) {
      line = getNextLine(fileIn);
      getLastCreatedDecision(gamePlan).setNeededItem(line);
      return true;
    }
    return false;
  }
}
