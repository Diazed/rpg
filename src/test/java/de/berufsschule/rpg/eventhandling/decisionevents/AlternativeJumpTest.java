package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlternativeJumpTest {

  private AlternativeJump systemUnderTest;
  private Decision testDecision;
  private Player testPlayer;

  @Before
  public void setUp() throws Exception {
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

    testDecision.setAlternativeJump("altjump");
    testDecision.setProbability(null);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityOverHundret() {

    testDecision.setAlternativeJump("altjump");
    testDecision.setProbability(101);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setNormalJumpWhenProbabiltyHundret() {

    testDecision.setAlternativeJump("altjump");
    testDecision.setJump("jump");
    testDecision.setProbability(100);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("jump", testPlayer.getPosition());
  }

  @Test
  public void setAlternativeJumpWhenProbabiltyHundret() {

    testDecision.setAlternativeJump("altjump");
    testDecision.setJump("jump");
    testDecision.setProbability(0);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, null);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("altjump", testPlayer.getPosition());
  }
}