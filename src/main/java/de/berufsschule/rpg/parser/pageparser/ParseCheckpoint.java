package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseCheckpoint extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#CHECKPOINT")){
      Page page = getLastCreatedPage(gamePlan);
      page.setCheckpoint(true);
      return true;
    }
    return false;
  }
}
