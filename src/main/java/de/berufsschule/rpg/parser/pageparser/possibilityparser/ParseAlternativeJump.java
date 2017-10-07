package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseAlternativeJump extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ALTERNATIVEJUMP")){
      line = getNextLine(fileIn);
      Possibility possibility = getLastCreatedPossibility(gamePlan);
      possibility.setAlternativeJump(line);
      return true;
    }
    return false;
  }
}
