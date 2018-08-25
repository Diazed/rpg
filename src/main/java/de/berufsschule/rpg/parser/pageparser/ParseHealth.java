package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseHealth extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.HEALTH)) {
      Integer healthManipulation = parseInt(parseModel.getAndSetNextLine());
      getLastCreatedPage(parseModel.getGamePlan()).setHealthManipulation(healthManipulation);
      return true;
    }
    return false;
  }
}
