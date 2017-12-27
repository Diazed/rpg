package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseAddSkillpoints extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (parseModel.getLine().contains("#ADDSKILLPOINTS")) {
      getLastCreatedPage(parseModel.getGamePlan())
          .setSkillPointManipulation(parseInt(parseModel.getNextLine()));
      return true;
    }
    return false;
  }
}
