package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseThirst extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#THIRST")) {
      Integer thirstManipulation = parseInt(getNextLine(fileIn));
      getLastCreatedPage(gamePlan).setThirstManipulation(thirstManipulation);
      return true;
    }
    return false;
  }
}
