package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.item.FoodItem;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseEating extends BaseParser{
  @Override
  public boolean parse(Game game, String line, Scanner fileIn) {
    if (line.contains("#EATING")) {
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        int eating = Integer.parseInt(prepareStringForParseInt(line));
        HashMap<String, Integer> indexes = getIndexes(game);
        Page latestPage = game.getPages().get(indexes.get("pageIndx"));
        Item latestItem = latestPage.getItems().get(indexes.get("itemIndx"));
        ((FoodItem)latestItem).setValue(eating);
        return true;
      }
    }
    return false;
  }
}
