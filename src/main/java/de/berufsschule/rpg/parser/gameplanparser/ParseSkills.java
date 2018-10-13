package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.SubParserRunner;
import de.berufsschule.rpg.parser.skillparser.SkillParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.SkillService;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ParseSkills extends SubParserRunner<SkillParser> implements GamePlanParser {

  private List<SkillParser> skillParsers;
  private SkillService skillService;

  @Autowired
  public ParseSkills(List<SkillParser> skillParsers, SkillService skillService) {
    this.skillParsers = skillParsers;
    this.skillService = skillService;
  }

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    return parse(parseModel);
  }

  @Override
  protected Command getStartCommand() {
    return Command.SKILLS;
  }

  @Override
  protected Command getEndCommand() {
    return Command.ENDSKILLS;
  }

  @Override
  protected BiFunction<SkillParser, ParseModel, Boolean> getParseMethod() {
    return SkillParser::parseSkill;
  }

  @Override
  protected List<SkillParser> getSubParser() {
    return skillParsers;
  }

  @Override
  protected void saveLastCreated(ParseModel parseModel) {
    Skill lastCreatedSkill = getLastCreatedSkill(parseModel.getGamePlan());
    if (lastCreatedSkill != null) {
      skillService.saveSkill(lastCreatedSkill);
    }
  }
}
