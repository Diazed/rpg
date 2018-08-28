package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseLearnSkill extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.LEARNSKILL)) {
      GamePlan gamePlan = parseModel.getGamePlan();
      Page page = getLastCreatedPage(gamePlan);
      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      optionalNextLine.ifPresent(s -> page.getSkills().add(findSkillByName(gamePlan, s)));
      return true;
    }
    return false;
  }
}
