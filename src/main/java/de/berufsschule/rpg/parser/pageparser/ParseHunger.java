package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseHunger extends BaseParser implements PageParser {
  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.HUNGER)) {
      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      optionalNextLine.ifPresent(
          s -> getLastCreatedPage(parseModel.getGamePlan()).setHungerManipulation(parseInt(s)));
      return true;
    }
    return false;
  }
}
