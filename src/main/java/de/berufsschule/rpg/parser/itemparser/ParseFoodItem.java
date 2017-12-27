package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.FoodItem;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseFoodItem extends BaseParser implements ItemParser {

  private ItemService itemService;

  @Autowired
  public ParseFoodItem(ItemService itemService) {
    this.itemService = itemService;
  }

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (parseModel.getLine().contains("#FOOD")) {

      Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
      if (lastCreatedItem != null) {
        itemService.saveItem(lastCreatedItem);
      }

      FoodItem foodItem = new FoodItem();
      String line = parseModel.getAndSetNextLine();
      foodItem.setValue(parseInt(line));
      foodItem.setConsumable(true);
      parseModel.getGamePlan().getItems().add(foodItem);
      return true;
    }
    return false;
  }

}
