package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.HealItem;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseHealItem extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#MEDICINE")) {
      HealItem healItem = new HealItem();
      line = getNextLine(fileIn);
      healItem.setValue(parseInt(line));
      healItem.setConsumable(true);
      gamePlan.getItems().add(healItem);
      return true;
    }
    return false;
  }
}
