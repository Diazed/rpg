package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Possibility;
import de.berufsschule.rpg.domain.model.Question;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.PossibilityService;
import org.springframework.stereotype.Component;

@Component
public class ParseQuestion extends BaseParser implements PossibilityParser {

  private PossibilityService possibilityService;

  public ParseQuestion(PossibilityService possibilityService) {
    this.possibilityService = possibilityService;
  }

  @Override
  public boolean parsePossibility(ParseModel parseModel) {
    if (parseModel.getLine().contains("#QUESTION")) {

      Possibility lastCreatedPossibility = getLastCreatedPossibility(parseModel.getGamePlan());
      if (lastCreatedPossibility != null) {
        possibilityService.savePossibility(lastCreatedPossibility);
      }

      Possibility possibility = new Question();
      getLastCreatedPage(parseModel.getGamePlan()).getPossibilities().add(possibility);
      return true;
    }
    return false;
  }
}
