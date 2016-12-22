package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseName extends BaseParser {
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#NAME")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        String name = getStringBetweenQuotationMarks(line);
        HashMap<String, Integer> indexes = getIndexes(game);
        Page latestPage = game.getPages().get(indexes.get("pageIndx"));
        latestPage.setName(name);
        return true;
      }
    }
    return false;
  }
}
