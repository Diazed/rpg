package de.berufsschule.rpg.eventhandling.possibilityevents;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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
  private Possibility testPossibility;
  private Player testPlayer;
  private Skill testSkill;
  private Page testPage;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    systemUnderTest = new SkillRequired((skillService));
    testPossibility = new Possibility();
    testPlayer = new Player();
    testSkill = new Skill();
    testPage = new Page();
    when(skillService.getSkillByName(any(String.class))).thenReturn(testSkill);
  }

  @Test
  public void returnFalseWhenPossibilityDoesNotRequireSkill() {
    testPossibility.setAlternativeJump("altjump");
    testPossibility.setSkillSuccessLvl(5);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenAlternativeJumpIsMissing() {
    testPossibility.setRequiredSkill("skill");
    testPossibility.setSkillSuccessLvl(5);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenSuccessLevelIsMissing() {
    testPossibility.setRequiredSkill("skill");
    testPossibility.setAlternativeJump("altjump");
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityIsSet() {
    testPossibility.setProbability(1);
    testPossibility.setRequiredSkill("skill");
    testPossibility.setAlternativeJump("altjump");
    testPossibility.setSkillSuccessLvl(5);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setAltJumpWhenSkillLvlToLowAndNoMinLvl() {
    testPossibility.setRequiredSkill("skill");
    testPossibility.setSkillSuccessLvl(5);
    testPossibility.setJump("jump");
    testPossibility.setAlternativeJump("altjump");
    testSkill.setLevel(3);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("altjump", testPlayer.getPosition());
  }

  @Test
  public void setAltJumpWhenSkillLvlToLowAndBelowMinLvl() {
    testPossibility.setRequiredSkill("skill");
    testPossibility.setSkillSuccessLvl(5);
    testPossibility.setJump("jump");
    testPossibility.setAlternativeJump("altjump");
    testPossibility.setSkillMinLvl(4);
    testSkill.setLevel(3);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("altjump", testPlayer.getPosition());
  }

  @Test
  public void setNormalJumpWhenSkillLvlEqualToSuccessLvl() {
    testPossibility.setRequiredSkill("skill");
    testPossibility.setSkillSuccessLvl(23);
    testPossibility.setJump("jump");
    testPossibility.setAlternativeJump("altjump");
    testPossibility.setSkillMinLvl(14);
    testSkill.setLevel(23);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("jump", testPlayer.getPosition());
  }

  @Test
  public void setAlternativeJumpWhenSkillLvlEqualToMinLvl() {
    testPossibility.setRequiredSkill("skill");
    testPossibility.setSkillSuccessLvl(23);
    testPossibility.setJump("jump");
    testPossibility.setAlternativeJump("altjump");
    testPossibility.setSkillMinLvl(14);
    testSkill.setLevel(14);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("altjump", testPlayer.getPosition());
  }
}