package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParseStorytext extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#STORYTEXT")) {
      String storytext = "";
      while (!line.contains("#TEND")){
        if (fileIn.hasNextLine()) {
          line = fileIn.nextLine();
          if (!line.endsWith(" "))
            line += " ";
          if (!line.contains("#TEND"))
            storytext += getStringBetweenQuotationMarks(line);
        }
      }
      List<Page> pages = game.getPages();
      int pageIndx = pages.size() - 1;
      pages.get(pageIndx).setStorytext(storytext);
      return true;
    }
    return false;
  }
}
