package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import de.berufsschule.rpg.item.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParseGiveItem extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#GIVEITEM")) {
      Item item = new Item();
      List<Page> pages = game.getPages();
      int pageIndx = pages.size() - 1;
      pages.get(pageIndx).getItems().add(item);
      return true;
    }
    return false;
  }
}
