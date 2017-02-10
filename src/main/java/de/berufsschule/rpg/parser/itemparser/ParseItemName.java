package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseItemName extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#NAME")) {
      line = getNextLine(fileIn);
      Item item = getLastCreatedItem(gamePlan);
      item.setName(line);
      return true;
    }
    return false;
  }
}
