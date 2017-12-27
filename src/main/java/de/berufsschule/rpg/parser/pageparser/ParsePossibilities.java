package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.PossibilityParser;
import de.berufsschule.rpg.services.PossibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ParsePossibilities extends BaseParser implements PageParser {

  private List<PossibilityParser> possibilityParsers;
  private PossibilityService possibilityService;

  @Autowired
  public ParsePossibilities(List<PossibilityParser> possibilityParsers,
      PossibilityService possibilityService) {
    this.possibilityParsers = possibilityParsers;
    this.possibilityService = possibilityService;
  }


  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (parseModel.getLine().contains("POSSIBILITIES")) {
      while (!parseModel.getLine().contains("#ENDPOSSIBILITIES")) {
        String line = parseModel.getAndSetNextLine();
        if (!line.startsWith("//") && !Objects.equals(line, "")) {
          for (PossibilityParser parser : possibilityParsers) {
            if (parser.parsePossibility(parseModel)) {
              break;
            }
          }
        }
      }
      Possibility lastCreatedPossibility = getLastCreatedPossibility(parseModel.getGamePlan());
      if (lastCreatedPossibility != null) {
        possibilityService.savePossibility(lastCreatedPossibility);
      }
      return true;
    }
    return false;
  }
}
