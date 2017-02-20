package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseGiveItem extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#GIVE")) {
      line = getNextLine(fileIn);
      Page page = getLastCreatedPage(gamePlan);
      page.getItems().add(line);
      return true;
    }
    return false;
  }
}