package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.HealItem;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Item;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseHealing extends BaseParser{

  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {

    if (line.contains("#HEALING")) {
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        int healing = Integer.parseInt(prepareStringForParseInt(line));
        HashMap<String, Integer> indexes = getIndexes(gamePlan);
        Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
        Item latestItem = latestPage.getItems().get(indexes.get("itemIndx"));
        ((HealItem)latestItem).setValue(healing);
        return true;
      }
    }
    return false;
  }
}
