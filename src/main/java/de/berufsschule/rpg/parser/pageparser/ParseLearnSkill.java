package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.springframework.stereotype.Component;

@Component
public class ParseLearnSkill extends BaseParser implements PageParser{

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.LEARNSKILL)) {
      GamePlan gamePlan = parseModel.getGamePlan();
            Page page = getLastCreatedPage(gamePlan);
      page.getSkills().add(findSkillByName(gamePlan, parseModel.getAndSetNextLine()));
            return true;
        }
        return false;
    }
}
