package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseStartPage extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#STARTPAGE")){
      line = getNextLine(fileIn);
      gamePlan.setStartPage(getLastCreatedPage(gamePlan).getId());
      return true;
    }
    return false;
  }
}
