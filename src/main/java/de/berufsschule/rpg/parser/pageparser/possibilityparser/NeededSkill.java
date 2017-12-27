package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class NeededSkill extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(ParseModel parseModel) {
    if (parseModel.getLine().contains("#REQUIREDSKILL")) {
      Possibility possibility = getLastCreatedPossibility(parseModel.getGamePlan());
      possibility.setRequiredSkill(parseModel.getAndSetNextLine());
      return true;
    }
    return false;
  }
}
