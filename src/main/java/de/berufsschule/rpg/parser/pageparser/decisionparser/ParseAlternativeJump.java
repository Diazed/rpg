package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseAlternativeJump extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ALTERNATIVEJUMP")){
      line = getNextLine(fileIn);
      Decision decision = getLastCreatedDecision(gamePlan);
      decision.setAlternativeJump(line);
      return true;
    }
    return false;
  }
}
