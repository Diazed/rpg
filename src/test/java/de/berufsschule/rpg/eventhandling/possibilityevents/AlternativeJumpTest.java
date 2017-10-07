package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlternativeJumpTest {

  private AlternativeJump systemUnderTest;
  private Possibility testPossibility;
  private Player testPlayer;

  @Before
  public void setUp() throws Exception {
    systemUnderTest = new AlternativeJump();
    testPossibility = new Possibility();
    testPlayer = new Player();
  }

  @Test
  public void returnFalseWhenAlternativeJumpMissing() {

    testPossibility.setProbability(5);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityUnderZero() {

    testPossibility.setAlternativeJump("altjump");
    testPossibility.setProbability(null);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenProbabilityOverHundret() {

    testPossibility.setAlternativeJump("altjump");
    testPossibility.setProbability(101);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, null);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setNormalJumpWhenProbabiltyHundret() {

    testPossibility.setAlternativeJump("altjump");
    testPossibility.setJump("jump");
    testPossibility.setProbability(100);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, null);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("jump", testPlayer.getPosition());
  }

  @Test
  public void setAlternativeJumpWhenProbabiltyHundret() {

    testPossibility.setAlternativeJump("altjump");
    testPossibility.setJump("jump");
    testPossibility.setProbability(0);
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, null);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("altjump", testPlayer.getPosition());
  }
}