package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseUseItem extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#USEITEM")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        String usedItem = getStringBetweenQuotationMarks(line);
        HashMap<String, Integer> indexes = getIndexes(gamePlan);
        Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
        latestPage.setUsedItem(usedItem);
        return true;
      }
    }
    return false;
  }
}
