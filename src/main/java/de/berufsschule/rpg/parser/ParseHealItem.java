package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.HealItem;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseHealItem extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#HEALITEM")) {
      HealItem item = new HealItem();
      HashMap<String, Integer> indexes = getIndexes(game);
      Page latestPage = game.getPages().get(indexes.get("pageIndx"));
      latestPage.getItems().add(item);
      return true;
    }
    return false;
  }
}
