package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.itemparser.ItemParser;
import de.berufsschule.rpg.parser.tools.Command;
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
    if (checkCommand(parseModel, Command.ITEMS)) {
      while (!checkCommand(parseModel, Command.ENDITEMS)) {

        if (parseModel.hasNextLine()) {
          String line = parseModel.getAndSetNextLine();
          if (validateLine(line)) {
            runEachItemParserTillLineIsParsed(parseModel);
          }
        } else {
          return false;
        }
      }
      saveLastCreated(parseModel);
      return true;
    }
    return false;
  }

  private void runEachItemParserTillLineIsParsed(ParseModel parseModel) {
    for (ItemParser parser : itemParsers) {
      if (parser.parseItem(parseModel))
        break;
    }
  }

  private void saveLastCreated(ParseModel parseModel) {
    Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
    if (lastCreatedItem != null) {
      itemService.saveItem(lastCreatedItem);
    }
  }

  private boolean validateLine(String line){
    return !line.startsWith("//") && !Objects.equals(line, "");
  }
}
