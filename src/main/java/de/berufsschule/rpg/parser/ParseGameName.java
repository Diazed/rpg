package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Game;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseGameName extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#GAMENAME")){
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        String gameName = getStringBetweenQuotationMarks(line);
        game.setName(gameName);
        return true;
      }
    }
    return false;
  }
}
