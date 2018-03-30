package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.DrinkItem;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseDrinkItem extends BaseParser implements ItemParser {

  private ItemService itemService;

  @Autowired
  public ParseDrinkItem(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (parseModel.getLine().contains("#DRINK")) {

      Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
      if (lastCreatedItem != null) {
        itemService.saveItem(lastCreatedItem);
      }

      DrinkItem drinkItem = new DrinkItem();
      String line = parseModel.getAndSetNextLine();
      drinkItem.setValue(parseInt(line));
      drinkItem.setConsumable(true);
      parseModel.getGamePlan().getItems().add(drinkItem);
      return true;
    }
    return false;
  }
}
