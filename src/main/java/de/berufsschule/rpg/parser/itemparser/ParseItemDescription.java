package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseItemDescription extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (parseModel.getLine().contains("#DESCRIPTION")) {
      String line = parseModel.getAndSetNextLine();
      Item item = getLastCreatedItem(parseModel.getGamePlan());
      item.setDescription(line);
      return true;
    }
    return false;
  }
}
