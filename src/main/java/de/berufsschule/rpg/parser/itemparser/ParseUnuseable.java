package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseUnuseable extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#UNUSEABLE")) {
      getLastCreatedItem(gamePlan).setConsumable(false);
      return true;
    }
    return false;
  }
}
