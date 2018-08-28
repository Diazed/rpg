package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseGiveItem extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.GIVE)) {
      Page page = getLastCreatedPage(parseModel.getGamePlan());
      String nextLine = "";
      while (!nextLine.contains("#") && parseModel.hasNextLine()) {

        Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
        if (optionalNextLine.isPresent()){
          nextLine = optionalNextLine.get();
          if (!nextLine.contains("#")) {
            page.getItems().add(findItemByName(parseModel.getGamePlan(), nextLine));
          }
        }
      }
      return true;
    }
    return false;
  }
}
