package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.skillparser.SkillParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ParseSkills extends BaseParser implements GamePlanParser {

  private List<SkillParser> skillParsers;
  private SkillService skillService;

  @Autowired
  public ParseSkills(List<SkillParser> skillParsers, SkillService skillService) {
    this.skillParsers = skillParsers;
    this.skillService = skillService;
  }

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.SKILLS)) {
      while (!checkCommand(parseModel, Command.ENDSKILLS)) {

        if (parseModel.hasNextLine()) {
          String line = parseModel.getAndSetNextLine();
          if (!line.startsWith("//") && !Objects.equals("", line) && !line.contains(Command.SKILLS.getCommand())) {
            for (SkillParser skillParser : skillParsers) {
              if (skillParser.parseSkill(parseModel)) {
                break;
              }
            }
          }
        } else {
          return false;
        }
      }
      Skill lastCreatedSkill = getLastCreatedSkill(parseModel.getGamePlan());
      if (lastCreatedSkill != null) {
        skillService.saveSkill(lastCreatedSkill);
      }
      return true;
    }
    return false;
  }
}
