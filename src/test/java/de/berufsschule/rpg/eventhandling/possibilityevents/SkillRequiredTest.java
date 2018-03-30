package de.berufsschule.rpg.eventhandling.possibilityevents;

import static org.mockito.Matchers.any;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Skill;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SkillRequiredTest {

  private SkillRequired systemUnderTest;
  private Decision testDecision;
  private Player testPlayer;
  private Skill testSkill;
  private Page testPage;

  @Before
  public void setUp() {
    systemUnderTest = new SkillRequired();
    testDecision = new Decision();
    testPlayer = new Player();
    testSkill = new Skill();
    testPage = new Page();
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