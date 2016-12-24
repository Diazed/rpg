package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
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