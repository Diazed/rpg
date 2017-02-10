package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseChance extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#CHANCE")){
      line = getNextLine(fileIn);
      int probability = parseInt(line);
      Decision decision = getLastCreatedDecision(gamePlan);
      decision.setProbability(probability);
      return true;
    }
    return false;
  }
}
