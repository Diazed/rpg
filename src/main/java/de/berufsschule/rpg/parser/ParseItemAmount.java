package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParseItemAmount extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#ITEMAMOUNT")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        int amount = Integer.parseInt(prepareStringForParseInt(line));
        List<Page> pages = game.getPages();
        int pageIndx = pages.size() - 1;
        int itemIndx = pages.get(pageIndx).getItems().size() - 1;
        pages.get(pageIndx).getItems().get(itemIndx).setAmount(amount);
        return true;
      }
    }
    return false;
  }
}
