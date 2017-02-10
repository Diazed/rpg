package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.DrinkItem;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseDrinkItem extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#DRINK")) {
      DrinkItem drinkItem = new DrinkItem();
      line = getNextLine(fileIn);
      drinkItem.setValue(parseInt(line));
      gamePlan.getItems().add(drinkItem);
      return true;
    }
    return false;
  }

}
