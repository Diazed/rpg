package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.stereotype.Component;

@Component
public class ParseSimpleItem extends BaseParser implements ItemParser {

  private ItemService itemService;

  public ParseSimpleItem(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.SIMPLEITEM)) {
      Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
      if (lastCreatedItem != null) {
        itemService.saveItem(lastCreatedItem);
      }
      parseModel.getGamePlan().getItems().add(new Item());
      return true;
    }
    return false;
  }
}
