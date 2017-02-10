package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.FoodItem;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseEating extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#EATING")) {
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        int eating = Integer.parseInt(prepareStringForParseInt(line));
        HashMap<String, Integer> indexes = getIndexes(gamePlan);
        Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
        Item latestItem = latestPage.getItems().get(indexes.get("itemIndx"));
        ((FoodItem)latestItem).setValue(eating);
        return true;
      }
    }
    return false;
  }
}
