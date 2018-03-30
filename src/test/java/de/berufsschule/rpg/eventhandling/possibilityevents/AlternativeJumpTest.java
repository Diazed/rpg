package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlternativeJumpTest {

  private AlternativeJump systemUnderTest;
  private Decision testDecision;
  private Player testPlayer;

  @Before
  public void setUp() {
    systemUnderTest = new AlternativeJump();
    testDecision = new Decision();
    testPlayer = new Player();
  }

  @Test
  public void returnFalseWhenAlternativeJumpMissing() {

    testDecision.setProbability(5);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityUnderZero() {

    testDecision.setAltJump(4);
    testDecision.setProbability(null);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityOverHundret() {

    testDecision.setAltJump(4);
    testDecision.setProbability(101);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setNormalJumpWhenProbabiltyHundret() {

    testDecision.setAltJump(4);
    testDecision.setMainJump(5);
    testDecision.setProbability(100);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(true, actual);
    Assert.assertEquals((Integer) 5, testPlayer.getPosition());
  }

  @Test
  public void setAlternativeJumpWhenProbabiltyHundret() {

    testDecision.setAltJump(4);
    testDecision.setMainJump(5);
    testDecision.setProbability(0);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(true, actual);
    Assert.assertEquals((Integer) 5, testPlayer.getPosition());
  }
}