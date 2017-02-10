package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseDecision extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#DECISION")) {
      Decision decision = new Decision();
      HashMap<String, Integer> indexes = getIndexes(gamePlan);
      Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
      latestPage.getDecisions().add(decision);
      return true;
    }
    return false;
  }
}
