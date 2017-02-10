package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseInjury extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#INJURY")){
      line = getNextLine(fileIn);
      int injury = parseInt(line);
      getLastCreatedDecision(gamePlan).setInjury(injury);
      return true;
    }
    return false;
  }
}
