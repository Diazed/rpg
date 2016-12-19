package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Decision;
import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Page;
import de.berufsschule.rpg.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParsePage implements Parser {
  @Override
  public boolean parse(Game game, String line) {

    if (Objects.equals(line, "#PAGE")){
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
