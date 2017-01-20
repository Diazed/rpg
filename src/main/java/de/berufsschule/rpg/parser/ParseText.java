package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseText extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#TEXT")) {
      if (fileIn.hasNextLine()) {
        line = fileIn.nextLine();
        String text = getStringBetweenQuotationMarks(line);
        HashMap<String, Integer> indexes = getIndexes(game);
        Page latestPage = game.getPages().get(indexes.get("pageIndx"));
        Decision latestDecision = latestPage.getDecisions().get(indexes.get("decisionIndx"));
        latestDecision.setText(text);
        return true;
      }
    }
    return false;
  }
}
