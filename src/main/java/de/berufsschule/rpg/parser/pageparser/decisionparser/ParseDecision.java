package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseDecision extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#DECISION")) {
      Decision decision = new Decision();
      getLastCreatedPage(gamePlan).getDecisions().add(decision);
      return true;
    }
    return false;
  }
}
