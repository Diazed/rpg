package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.skillparser.SkillParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.SkillService;
import java.util.Optional;
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

        Optional<String> optionalNextLine = parseModel.getAndSetNextLine();

        if (optionalNextLine.isPresent()) {
          String line = optionalNextLine.get();
          if (!line.startsWith("//") && !Objects.equals("", line)) {
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
      saveLastCreatedSkill(parseModel);
      return true;
    }
    return false;
  }

  private void saveLastCreatedSkill(ParseModel parseModel) {
    Skill lastCreatedSkill = getLastCreatedSkill(parseModel.getGamePlan());
    if (lastCreatedSkill != null) {
      skillService.saveSkill(lastCreatedSkill);
    }
  }
}
