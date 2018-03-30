package de.berufsschule.rpg.eventhandling.gameevents;

import de.berufsschule.rpg.domain.model.Game;
import de.berufsschule.rpg.domain.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExperienceTest {

  private Experience systemUnderTest;
  private Player testPlayer;
  private Game testGame;

  @Before
  public void setUp() {
    testGame = new Game();
    testPlayer = new Player();
    testPlayer.setGame(testGame);
    systemUnderTest = new Experience();
  }

  @Test
  public void defaultXpIncrementOfTwenty() {

    testPlayer.setPlayerLvl(10);
    testPlayer.setExp(5);
    Integer expected = 25;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getExp());
  }

  @Test
  public void preferIncrementDefinedInGame() {

    testGame.getGamePlan().setRoundExp(5);
    testPlayer.setPlayerLvl(10);
    testPlayer.setExp(5);
    Integer expected = 10;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getExp());
  }

  @Test
  public void increasePlayerLevelWhenNeededXpReached() {

    testGame.getGamePlan().setRoundExp(15);
    testPlayer.setPlayerLvl(0);
    testPlayer.setExp(0);
    Integer expected = 1;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getPlayerLvl());
  }

  @Test
  public void increasePlayerSkilPointsWhenNeededXpReached() {

    testGame.getGamePlan().setRoundExp(15);
    testPlayer.setPlayerLvl(0);
    testPlayer.setExp(0);
    Integer expected = 10;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getSkillPoints());
  }

  @Test
  public void levelProgressIsSetCorrectly() {

    testGame.getGamePlan().setRoundExp(10);
    testPlayer.setPlayerLvl(2);
    testPlayer.setExp(20);
    Integer expected = 50;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getLevelProgress());
  }

  @Test
  public void neededXpIsSetCorrectly() {

    testGame.getGamePlan().setRoundExp(10);
    testPlayer.setPlayerLvl(3);
    testPlayer.setExp(20);
    Integer expected = 100;
    systemUnderTest.event(testPlayer);
    Assert.assertEquals(expected, testPlayer.getNeededExp());
  }
}