package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseHealth extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#HEALTH")) {
      Integer healthManipulation = parseInt(getNextLine(fileIn));
      getLastCreatedPage(gamePlan).setHealthManipulation(healthManipulation);
      return true;
    }
    return false;
  }
}
