package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseAddSkillpoints extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ADDSKILLPOINTS")) {
      getLastCreatedPage(gamePlan).setSkillPointManipulation(parseInt(getNextLine(fileIn)));
      return true;
    }
    return false;
  }
}
