package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseSimpleItem implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#SIMPLEITEM")) {
      gamePlan.getItems().add(new Item());
      return true;
    }
    return false;
  }
}
