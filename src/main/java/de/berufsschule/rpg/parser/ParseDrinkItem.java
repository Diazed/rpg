package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.item.DrinkItem;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseDrinkItem extends BaseParser {
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#DRINKITEM")) {
      DrinkItem item = new DrinkItem();
      HashMap<String, Integer> indexes = getIndexes(game);
      Page latestPage = game.getPages().get(indexes.get("pageIndx"));
      latestPage.getItems().add(item);
      return true;
    }
    return false;
  }
}
