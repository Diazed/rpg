package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParseText extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#TEXT")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        String text = getStringBetweenQuotationMarks(line);
        List<Page> pages = game.getPages();
        int pageIndx = pages.size() - 1;
        int decisionIndx = pages.get(pageIndx).getDecisions().size() - 1;
        pages.get(pageIndx).getDecisions().get(decisionIndx).setText(text);
        return true;
      }
    }
    return false;
  }
}