package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseGiveItem extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (parseModel.getLine().contains("#GIVE")) {
      Page page = getLastCreatedPage(parseModel.getGamePlan());
      String nextLine = "";
      while (!nextLine.contains("#") && parseModel.hasNextLine()) {
        nextLine = parseModel.getNextLine();
        if (!nextLine.contains("#")) {
          page.getItems().add(findItemByName(parseModel.getGamePlan(), parseModel.getLine()));
        }
      }
      return true;
    }
    return false;
  }
}
