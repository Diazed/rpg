package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseUnuseable extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.UNUSEABLE)) {
      getLastCreatedItem(parseModel.getGamePlan()).setConsumable(false);
      return true;
    }
    return false;
  }
}
