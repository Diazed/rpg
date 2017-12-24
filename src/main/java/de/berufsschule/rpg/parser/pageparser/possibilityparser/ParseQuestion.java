package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Question;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.PossibilityService;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseQuestion extends BaseParser implements PossibilityParser {

  private PossibilityService possibilityService;

  public ParseQuestion(PossibilityService possibilityService) {
    this.possibilityService = possibilityService;
  }

  @Override
  public boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#QUESTION")) {

      Possibility lastCreatedPossibility = getLastCreatedPossibility(gamePlan);
      if (lastCreatedPossibility != null) {
        possibilityService.savePossibility(lastCreatedPossibility);
      }

      Possibility possibility = new Question();
      getLastCreatedPage(gamePlan).getPossibilities().add(possibility);
      return true;
    }
    return false;
  }
}
