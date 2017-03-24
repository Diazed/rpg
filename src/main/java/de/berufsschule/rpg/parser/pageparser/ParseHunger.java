package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseHunger extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#HUNGER")) {
      Integer hungerManipulation = parseInt(getNextLine(fileIn));
      getLastCreatedPage(gamePlan).setHungerManipulation(hungerManipulation);
      return true;
    }
    return false;
  }
}
