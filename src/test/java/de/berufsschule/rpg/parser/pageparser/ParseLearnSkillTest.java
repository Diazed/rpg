package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseLearnSkillTest extends PageParserTest<ParseLearnSkill> {

  private static final Skill TEST_SKILL_ONE = new Skill();
  private static final Skill TEST_SKILL_TWO = new Skill();
  private static final String TEST_SKILL_ONE_NAME = "Test Skill";
  private static final String TEST_SKILL_TWO_NAME = "Skill";

  @Override
  protected ParseLearnSkill getSystemUnderTest() {
    return new ParseLearnSkill();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.LEARNSKILL;
  }

  @Test
  public void learnedSkillIsAdded() {

    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of(TEST_SKILL_ONE_NAME));

    boolean returnValue = systemUnderTest.parsePage(parseModel);

    assertThat(returnValue).isTrue();
    assertThat(page.getSkills()).isNotEmpty();
    assertThat(page.getSkills()).contains(TEST_SKILL_ONE);
    assertThat(page.getSkills()).doesNotContain(TEST_SKILL_TWO);
  }

  @Override
  protected GamePlan getGamePlan() {

    GamePlan manipulatedGamePlan = super.getGamePlan();

    TEST_SKILL_ONE.setName(TEST_SKILL_ONE_NAME);
    TEST_SKILL_TWO.setName(TEST_SKILL_TWO_NAME);

    manipulatedGamePlan.getSkills().add(TEST_SKILL_ONE);
    manipulatedGamePlan.getSkills().add(TEST_SKILL_TWO);

    return manipulatedGamePlan;
  }
}