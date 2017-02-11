package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.itemparser.ItemParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ParseItems implements GamePlanParser {

  private List<ItemParser> itemParsers;

  @Autowired
  public ParseItems(List<ItemParser> itemParsers) {
    this.itemParsers = itemParsers;
  }

  @Override
  public boolean parseGamePlan(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ITEMS")) {
      while (!line.contains("#ENDITEMS")) {

        if (fileIn.hasNextLine()) {
          line = fileIn.nextLine();
          if (!line.startsWith("//") && !Objects.equals(line, "")) {
            for (ItemParser parser : itemParsers) {

              if (parser.parseItem(gamePlan, line, fileIn))
                break;
            }
          }
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
}
