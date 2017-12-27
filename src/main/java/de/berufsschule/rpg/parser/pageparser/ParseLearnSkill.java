package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseLearnSkill extends BaseParser implements PageParser{

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (parseModel.getLine().contains("#LEARNSKILL")) {
      GamePlan gamePlan = parseModel.getGamePlan();
            Page page = getLastCreatedPage(gamePlan);
      page.getSkills().add(findSkillByName(gamePlan, parseModel.getAndSetNextLine()));
            return true;
        }
        return false;
    }
}
