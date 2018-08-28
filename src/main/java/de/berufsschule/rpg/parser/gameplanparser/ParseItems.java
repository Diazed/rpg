package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.itemparser.ItemParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.ItemService;
import java.util.Optional;
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
      return loopItemSegmentLines(parseModel);
    }
    return false;
  }

  private boolean loopItemSegmentLines(ParseModel parseModel) {
    while (!checkCommand(parseModel, Command.ENDITEMS)) {
      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      if (optionalNextLine.isPresent()) {
        runEachItemParser(parseModel, optionalNextLine.get());
      } else {
        return false;
      }
    }
    saveLastCreatedItem(parseModel);
    return true;
  }

  private void runEachItemParser(ParseModel parseModel, String line) {
    if (validateLine(line)) {
      for (ItemParser parser : itemParsers) {
        if (parser.parseItem(parseModel)) {
          break;
        }
      }
    }
  }

  private void saveLastCreatedItem(ParseModel parseModel) {
    Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
    if (lastCreatedItem != null) {
      itemService.saveItem(lastCreatedItem);
    }
  }

  private boolean validateLine(String line) {
    return !line.startsWith("//") && !Objects.equals(line, "");
  }
}
