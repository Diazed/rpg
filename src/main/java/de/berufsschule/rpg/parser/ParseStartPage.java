package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Game;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseStartPage extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#STARTPAGE")){
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        String startPage = getStringBetweenQuotationMarks(line);
        game.setStartPage(startPage);
        return true;
      }
    }
    return false;
  }
}
