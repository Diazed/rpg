package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseItemDescription extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.DESCRIPTION)) {
      String line = parseModel.getAndSetNextLine();
      Item item = getLastCreatedItem(parseModel.getGamePlan());
      item.setDescription(line);
      return true;
    }
    return false;
  }
}
