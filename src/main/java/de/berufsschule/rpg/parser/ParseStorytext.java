package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

          if (!line.contains("#TEND")){
            line = getStringBetweenQuotationMarks(line);
            if (!line.endsWith(" "))
              line += " ";
            storytext += line;
          }

        }
      }
      HashMap<String, Integer> indexes = getIndexes(game);
      Page latestPage = game.getPages().get(indexes.get("pageIndx"));
      latestPage.setStorytext(storytext);
      return true;
    }
    return false;
  }
}
