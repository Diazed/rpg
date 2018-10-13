package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.SubParserRunner;
import de.berufsschule.rpg.parser.itemparser.ItemParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.ItemService;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ParseItems extends SubParserRunner<ItemParser> implements GamePlanParser {

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
    return parse(parseModel);
  }

  @Override
  protected Command getStartCommand() {
    return Command.ITEMS;
  }

  @Override
  protected Command getEndCommand() {
    return Command.ENDITEMS;
  }

  @Override
  protected BiFunction<ItemParser, ParseModel, Boolean> getParseMethod() {
    return ItemParser::parseItem;
  }

  @Override
  protected List<ItemParser> getSubParser() {
    return itemParsers;
  }

  @Override
  protected void saveLastCreated(ParseModel parseModel) {
    Item lastCreatedItem = getLastCreatedItem(parseModel.getGamePlan());
    if (lastCreatedItem != null) {
      itemService.saveItem(lastCreatedItem);
    }
  }
}
