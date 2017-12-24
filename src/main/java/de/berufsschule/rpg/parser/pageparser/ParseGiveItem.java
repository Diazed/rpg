package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseGiveItem extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#GIVE")) {
      Page page = getLastCreatedPage(gamePlan);
      String nextLine = "";
      while (!nextLine.contains("#") && fileIn.hasNextLine()) {
        nextLine = getNextLine(fileIn);
        if (!nextLine.contains("#")) {
          page.getItems().add(findItemByName(gamePlan, line));
        }
      }
      return true;
    }
    return false;
  }
}
