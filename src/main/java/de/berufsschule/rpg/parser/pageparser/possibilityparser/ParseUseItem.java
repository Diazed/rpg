package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseUseItem extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(ParseModel parseModel) {
    if (parseModel.getLine().contains("#USE")) {
      getLastCreatedPossibility(parseModel.getGamePlan())
          .setUsedItem(parseModel.getAndSetNextLine());
      return true;
    }
    return false;
  }
}
