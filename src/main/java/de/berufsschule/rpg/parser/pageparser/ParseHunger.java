package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseHunger extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (parseModel.getLine().contains("#HUNGER")) {
      Integer hungerManipulation = parseInt(parseModel.getAndSetNextLine());
      getLastCreatedPage(parseModel.getGamePlan()).setHungerManipulation(hungerManipulation);
      return true;
    }
    return false;
  }
}
