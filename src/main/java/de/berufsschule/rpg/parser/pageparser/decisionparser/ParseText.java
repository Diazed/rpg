package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseText extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#TEXT")) {
      line = getNextLine(fileIn);
      Decision decision = getLastCreatedDecision(gamePlan);
      decision.setText(line);
      return true;
    }
    return false;
  }
}
