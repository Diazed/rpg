package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.itemparser.ItemParser;
import de.berufsschule.rpg.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ParseItems extends BaseParser implements GamePlanParser {

  private List<ItemParser> itemParsers;
  private ItemService itemService;

  @Autowired
  public ParseItems(List<ItemParser> itemParsers,
      ItemService itemService) {
    this.itemParsers = itemParsers;
    this.itemService = itemService;
  }

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (parseModel.getLine().contains("#ITEMS")) {
      while (!parseModel.getLine().contains("#ENDITEMS")) {

        if (parseModel.hasNextLine()) {
          String line = parseModel.getAndSetNextLine();
          if (!line.startsWith("//") && !Objects.equals(line, "")) {
            for (ItemParser parser : itemParsers) {

              if (parser.parseItem(parseModel))
                break;
            }
          }
        } else {
          return false;
        }
      }
      Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
      if (lastCreatedItem != null) {
        itemService.saveItem(lastCreatedItem);
      }
      return true;
    }
    return false;
  }
}
