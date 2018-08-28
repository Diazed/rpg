package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.HealItem;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.ItemService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseHealItem extends BaseParser implements ItemParser {

  private ItemService itemService;

  @Autowired
  public ParseHealItem(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.MEDICINE)) {

      saveLastCreatedItem(parseModel);

      HealItem healItem = new HealItem();

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      if (optionalNextLine.isPresent()){
        healItem.setValue(parseInt(optionalNextLine.get()));
        healItem.setConsumable(true);
        parseModel.getGamePlan().getItems().add(healItem);
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
