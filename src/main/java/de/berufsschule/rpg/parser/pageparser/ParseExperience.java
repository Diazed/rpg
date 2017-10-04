package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseExperience extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#XP")) {
      Integer additionalXp = parseInt(getNextLine(fileIn));
      getLastCreatedPage(gamePlan).setXpManipulation(additionalXp);
      return true;
    }
    return false;
  }
}