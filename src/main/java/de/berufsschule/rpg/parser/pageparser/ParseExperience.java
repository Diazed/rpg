package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseExperience extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.XP)) {
      Integer additionalXp = parseInt(parseModel.getAndSetNextLine());
      getLastCreatedPage(parseModel.getGamePlan()).setXpManipulation(additionalXp);
      return true;
    }
    return false;
  }
}