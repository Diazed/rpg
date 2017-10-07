package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillMinLevel extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("SKILLMINLVL")) {
      Possibility possibility = getLastCreatedPossibility(gamePlan);
      possibility.setSkillMinLvl(parseInt(getNextLine(fileIn)));
      return true;
    }
    return false;
  }
}
