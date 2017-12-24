package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThirstTest {

  private Thirst systemUnderTest;
  private Player testPlayer;
  private Game testGame;

  @Before
  public void setUp() {
    testGame = new Game();
    testPlayer = new Player();
    testPlayer.setAlive(true);
    testPlayer.setGame(testGame);
    systemUnderTest = new Thirst();
  }

  @Test
  public void defaultThirstIncrementOfFive() {
    testPlayer.setThirst(0);
    Integer expected = 5;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getThirst());
    Assert.assertEquals(true, testPlayer.getAlive());
  }

  @Test
  public void preferIncrementDefinedInGame() {
    testPlayer.setThirst(0);
    testGame.getGamePlan().setRoundThirst(1);
    Integer expected = 1;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getThirst());
    Assert.assertEquals(true, testPlayer.getAlive());
  }

  @Test
  public void playerDehydratesWhenThirstExceedsHundret() {
    testPlayer.setThirst(95);
    testGame.getGamePlan().setRoundThirst(10);
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(false, testPlayer.getAlive());
  }

}