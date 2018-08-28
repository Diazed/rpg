package de.berufsschule.rpg.parser.gameplanparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.parenttests.SubParserRunnerTest;
import de.berufsschule.rpg.parser.skillparser.ParseSkillDescription;
import de.berufsschule.rpg.parser.skillparser.ParseSkillName;
import de.berufsschule.rpg.parser.skillparser.SkillParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.SkillService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ParseSkillsTest extends SubParserRunnerTest<ParseSkills, SkillParser, SkillService> {

  @Mock
  private ParseSkillDescription parseSkillDescription;
  @Mock
  private ParseSkillName parseSkillName;
  @Mock
  private SkillService skillService;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    when(parseSkillName.parseSkill(parseModel)).thenReturn(true);
  }

  @Override
  protected ParseSkills createSystemUnderTest(List<SkillParser> subParser, SkillService service) {
    return new ParseSkills(subParser, service);
  }

  @Override
  protected List<SkillParser> createSubParserMockList() {
    return Arrays.asList(parseSkillDescription, parseSkillName);
  }

  @Override
  protected SkillService createService() {
    return skillService;
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
  protected boolean runParser(ParseSkills systemUnderTest, ParseModel parseModel) {
    return systemUnderTest.parseGamePlan(parseModel);
  }

  @Test
  public void runsAllItemParser() {

    boolean returnValue = systemUnderTest.parseGamePlan(parseModel);

    verify(parseSkillDescription, atLeastOnce()).parseSkill(parseModel);
    verify(parseSkillName, atLeastOnce()).parseSkill(parseModel);
    assertThat(returnValue).isTrue();
  }

  @Test
  public void lastCreatedSkillIsSaved() {
    Skill skillToSave = new Skill();
    when(gamePlan.getSkills()).thenReturn(Collections.singletonList(skillToSave));
    boolean returnValue = systemUnderTest.parseGamePlan(parseModel);
    verify(skillService).saveSkill(skillToSave);
    assertThat(returnValue).isTrue();
  }

}