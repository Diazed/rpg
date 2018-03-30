package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Possibility;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillMinLevel extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(ParseModel parseModel) {
    if (parseModel.getLine().contains("SKILLMINLVL")) {
      Possibility possibility = getLastCreatedPossibility(parseModel.getGamePlan());
      possibility.setSkillMinLvl(parseInt(parseModel.getNextLine()));
      return true;
    }
    return false;
  }
}
