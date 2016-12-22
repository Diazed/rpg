package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Decision;
import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseDecision extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#DECISION")) {
      Decision decision = new Decision();
      HashMap<String, Integer> indexes = getIndexes(game);
      Page latestPage = game.getPages().get(indexes.get("pageIndx"));
      latestPage.getDecisions().add(decision);
      return true;
    }
    return false;
  }
}
