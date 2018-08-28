package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.DrinkItem;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.ItemService;
import java.util.Optional;
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
    if (checkCommand(parseModel, Command.DRINK)) {

      saveLastCreatedItem(parseModel);

      DrinkItem drinkItem = new DrinkItem();

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();

      if (optionalNextLine.isPresent()){
        drinkItem.setValue(parseInt(optionalNextLine.get()));
        drinkItem.setConsumable(true);
        parseModel.getGamePlan().getItems().add(drinkItem);
      }

      return true;
    }
    return false;
  }

  private void saveLastCreatedItem(ParseModel parseModel) {
    Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
    if (lastCreatedItem != null) {
      itemService.saveItem(lastCreatedItem);
    }
  }
}
