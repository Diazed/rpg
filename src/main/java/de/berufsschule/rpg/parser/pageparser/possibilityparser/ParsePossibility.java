package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParsePossibility extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#DECISION")) {
      Possibility possibility = new Possibility();
      getLastCreatedPage(gamePlan).getPossibilities().add(possibility);
      return true;
    }
    return false;
  }
}
