package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseChance extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#CHANCE")){
      if (fileIn.hasNext()){
        line = fileIn.nextLine();
        int probability = Integer.parseInt(prepareStringForParseInt(line));
        HashMap<String, Integer> indexes = getIndexes(gamePlan);
        Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
        Decision latestDecision = latestPage.getDecisions().get(indexes.get("decisionIndx"));
        latestDecision.setProbability(probability);
        return true;
      }
    }
    return false;
  }
}
