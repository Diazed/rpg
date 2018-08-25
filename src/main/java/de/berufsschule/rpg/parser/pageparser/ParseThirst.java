package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseThirst extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.THIRST)) {
      Integer thirstManipulation = parseInt(parseModel.getAndSetNextLine());
      getLastCreatedPage(parseModel.getGamePlan()).setThirstManipulation(thirstManipulation);
      return true;
    }
    return false;
  }
}
