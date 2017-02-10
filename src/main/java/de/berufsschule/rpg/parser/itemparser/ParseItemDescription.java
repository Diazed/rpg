package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseItemDescription extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#DESCRIPTION")) {
      line = getNextLine(fileIn);
      Item item = getLastCreatedItem(gamePlan);
      item.setDescription(line);
      return true;
    }
    return false;
  }
}
