package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.FoodItem;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseFoodItem extends BaseParser {
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#FOODITEM")) {
      FoodItem item = new FoodItem();
      HashMap<String, Integer> indexes = getIndexes(gamePlan);
      Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
      latestPage.getItems().add(item);
      return true;
    }
    return false;
  }
}
