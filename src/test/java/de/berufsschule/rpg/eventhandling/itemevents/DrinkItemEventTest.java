package de.berufsschule.rpg.eventhandling.itemevents;

import de.berufsschule.rpg.domain.model.DrinkItem;
import de.berufsschule.rpg.domain.model.HealItem;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DrinkItemEventTest {

  private DrinkItemEvent systemUnderTest;
  private Item testItem;
  private Player testPlayer;

  @Before
  public void setUp() {
    systemUnderTest = new DrinkItemEvent();
    testItem = new DrinkItem();
    testPlayer = new Player();
  }

  @Test
  public void ignoreNonDrinkItems() {
    testItem = new HealItem();
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void thirstValueGetsReducedByItemValue() {
    ((DrinkItem) testItem).setValue(3);
    testPlayer.setThirst(5);
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(true, actual);
    Integer expected = 2;
    Assert.assertEquals(expected, testPlayer.getThirst());
  }

  @Test
  public void thirstValueDoesntFallBelowZero() {
    ((DrinkItem) testItem).setValue(10);
    testPlayer.setThirst(5);
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(true, actual);
    Integer expected = 0;
    Assert.assertEquals(expected, testPlayer.getThirst());
  }

}