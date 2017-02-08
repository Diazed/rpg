package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Game;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseDeathPage extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#DEATHPAGE")){
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        String deathPage = getStringBetweenQuotationMarks(line);
        game.setDeathPage(deathPage);
        return true;
      }
    }
    return false;
  }
}
