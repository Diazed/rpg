package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.domain.model.Game;
import de.berufsschule.rpg.domain.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HungerTest {

  private Hunger systemUnderTest;
  private Player testPlayer;
  private Game testGame;

  @Before
  public void setUp() {
    testGame = new Game();
    testPlayer = new Player();
    testPlayer.setAlive(true);
    testPlayer.setGame(testGame);
    systemUnderTest = new Hunger();
  }

  @Test
  public void defaultHungerIncrementOfThree() {
    testPlayer.setHunger(0);
    Integer expected = 3;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getHunger());
    Assert.assertEquals(true, testPlayer.getAlive());
  }

  @Test
  public void preferIncrementDefinedInGame() {
    testPlayer.setHunger(0);
    testGame.getGamePlan().setRoundHunger(1);
    Integer expected = 1;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getHunger());
    Assert.assertEquals(true, testPlayer.getAlive());
  }

  @Test
  public void playerStarvesWhenHungerExceedsHundret() {
    testPlayer.setHunger(95);
    testGame.getGamePlan().setRoundHunger(10);
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(false, testPlayer.getAlive());
  }

}