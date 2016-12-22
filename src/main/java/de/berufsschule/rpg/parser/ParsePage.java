package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Decision;
import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import de.berufsschule.rpg.item.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ParsePage extends BaseParser {
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {

    if (line.contains("#PAGE")){
      Page page = new Page();
      List<Decision> decisions = new ArrayList<Decision>();
      List<Item> items = new ArrayList<Item>();
      page.setDecisions(decisions);
      page.setItems(items);
      game.getPages().add(page);
      return true;
    }
    return false;

  }
}
