package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Item;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseItemAmount extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ITEMAMOUNT")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        int amount = Integer.parseInt(prepareStringForParseInt(line));
        HashMap<String, Integer> indexes = getIndexes(gamePlan);
        Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
        Item latestItem = latestPage.getItems().get(indexes.get("itemIndx"));
        latestItem.setAmount(amount);
        return true;
      }
    }
    return false;
  }
}
