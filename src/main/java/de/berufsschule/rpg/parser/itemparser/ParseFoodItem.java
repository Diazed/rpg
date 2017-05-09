package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.FoodItem;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseFoodItem extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#FOOD")) {
      FoodItem foodItem = new FoodItem();
      line = getNextLine(fileIn);
      foodItem.setValue(parseInt(line));
      foodItem.setConsumable(true);
      gamePlan.getItems().add(foodItem);
      return true;
    }
    return false;
  }

}
