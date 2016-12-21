package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParseName extends BaseParser {
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#NAME")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        String name = getStringBetweenQuotationMarks(line);
        List<Page> pages = game.getPages();
        int pageIndx = pages.size() - 1;
        Page page = pages.get(pageIndx);
        page.setName(name);
        return true;
      }
    }
    return false;
  }
}
