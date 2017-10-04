package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class NeededSkill extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#REQUIREDSKILL")) {
      line = getNextLine(fileIn);
      Decision decision = getLastCreatedDecision(gamePlan);
      decision.setRequiredSkill(line);
      return true;
    }
    return false;
  }
}
