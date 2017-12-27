package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.PossibilityService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseDecision extends BaseParser implements PossibilityParser {

  private PossibilityService possibilityService;

  public ParseDecision(PossibilityService possibilityService) {
    this.possibilityService = possibilityService;
  }

  @Override
  public boolean parsePossibility(ParseModel parseModel) {
    if (parseModel.getLine().contains("#DECISION")) {

      Possibility lastCreatedPossibility = getLastCreatedPossibility(parseModel.getGamePlan());
      if (lastCreatedPossibility != null) {
        possibilityService.savePossibility(lastCreatedPossibility);
      }

      Possibility possibility = new Decision();
      getLastCreatedPage(parseModel.getGamePlan()).getPossibilities().add(possibility);
      return true;
    }
    return false;
  }
}
