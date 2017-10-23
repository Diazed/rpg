package de.berufsschule.rpg.eventhandling.possibilityevents;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.services.SkillService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SkillRequiredTest {

  @Mock
  private SkillService skillService;

  private SkillRequired systemUnderTest;
  private Decision testDecision;
  private Player testPlayer;
  private Skill testSkill;
  private Page testPage;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    systemUnderTest = new SkillRequired((skillService));
    testDecision = new Decision();
    testPlayer = new Player();
    testSkill = new Skill();
    testPage = new Page();
    when(skillService.getSkillByName(any(String.class))).thenReturn(testSkill);
  }

  @Test
  public void returnFalseWhenPossibilityDoesNotRequireSkill() {
    testDecision.setAltJump(5);
    testDecision.setSkillSuccessLvl(5);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenAlternativeJumpIsMissing() {
    testDecision.setRequiredSkill("skill");
    testDecision.setSkillSuccessLvl(5);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenSuccessLevelIsMissing() {
    testDecision.setRequiredSkill("skill");
    testDecision.setAltJump(5);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityIsSet() {
    testDecision.setProbability(1);
    testDecision.setRequiredSkill("skill");
    testDecision.setAltJump(5);
    testDecision.setSkillSuccessLvl(5);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setAltJumpWhenSkillLvlToLowAndNoMinLvl() {
    testDecision.setRequiredSkill("skill");
    testDecision.setSkillSuccessLvl(5);
    testDecision.setMainJump(5);
    testDecision.setAltJump(5);
    testSkill.setLevel(3);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals((Integer) 5, testPlayer.getPosition());
  }

  @Test
  public void setAltJumpWhenSkillLvlToLowAndBelowMinLvl() {
    testDecision.setRequiredSkill("skill");
    testDecision.setSkillSuccessLvl(5);
    testDecision.setMainJump(3);
    testDecision.setAltJump(5);
    testDecision.setSkillMinLvl(4);
    testSkill.setLevel(3);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals((Integer) 5, testPlayer.getPosition());
  }

  @Test
  public void setNormalJumpWhenSkillLvlEqualToSuccessLvl() {
    testDecision.setRequiredSkill("skill");
    testDecision.setSkillSuccessLvl(23);
    testDecision.setMainJump(3);
    testDecision.setAltJump(5);
    testDecision.setSkillMinLvl(14);
    testSkill.setLevel(23);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals((Integer) 3, testPlayer.getPosition());
  }

  @Test
  public void setAlternativeJumpWhenSkillLvlEqualToMinLvl() {
    testDecision.setRequiredSkill("skill");
    testDecision.setSkillSuccessLvl(23);
    testDecision.setMainJump(4);
    testDecision.setAltJump(3);
    testDecision.setSkillMinLvl(14);
    testSkill.setLevel(14);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals((Integer) 3, testPlayer.getPosition());
  }
}