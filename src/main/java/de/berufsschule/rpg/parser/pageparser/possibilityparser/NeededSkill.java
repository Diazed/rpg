package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class NeededSkill extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#REQUIREDSKILL")) {
      line = getNextLine(fileIn);
      Possibility possibility = getLastCreatedPossibility(gamePlan);
      possibility.setRequiredSkill(line);
      return true;
    }
    return false;
  }
}
