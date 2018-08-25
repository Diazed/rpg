package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseCheckpoint extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.CHECKPOINT)) {
      Page page = getLastCreatedPage(parseModel.getGamePlan());
      page.setCheckpoint(true);
      return true;
    }
    return false;
  }
}
