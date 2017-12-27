package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseHealth extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (parseModel.getLine().contains("#HEALTH")) {
      Integer healthManipulation = parseInt(parseModel.getAndSetNextLine());
      getLastCreatedPage(parseModel.getGamePlan()).setHealthManipulation(healthManipulation);
      return true;
    }
    return false;
  }
}
