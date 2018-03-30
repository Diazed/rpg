package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseUnuseable extends BaseParser implements ItemParser {

  @Override
  public boolean parseItem(ParseModel parseModel) {
    if (parseModel.getLine().contains("#UNUSEABLE")) {
      getLastCreatedItem(parseModel.getGamePlan()).setConsumable(false);
      return true;
    }
    return false;
  }
}
