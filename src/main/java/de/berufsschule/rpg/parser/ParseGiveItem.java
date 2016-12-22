package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import de.berufsschule.rpg.item.Item;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseGiveItem extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#GIVEITEM")) {
      Item item = new Item();
      HashMap<String, Integer> indexes = getIndexes(game);
      Page latestPage = game.getPages().get(indexes.get("pageIndx"));
      latestPage.getItems().add(item);
      return true;
    }
    return false;
  }
}
