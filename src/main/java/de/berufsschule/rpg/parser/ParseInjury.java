package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseInjury extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {

    if (line.contains("#INJURY")){
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        int injury = Integer.parseInt(prepareStringForParseInt(line));
        HashMap<String, Integer> indexes = getIndexes(gamePlan);
        Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
        Decision latestDecision = latestPage.getDecisions().get(indexes.get("decisionIndx"));
        latestDecision.setInjury(injury);
      }
      return true;
    }
    return false;
  }
}
