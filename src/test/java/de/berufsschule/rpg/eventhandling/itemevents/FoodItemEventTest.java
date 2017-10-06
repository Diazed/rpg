package de.berufsschule.rpg.eventhandling.itemevents;

import de.berufsschule.rpg.model.DrinkItem;
import de.berufsschule.rpg.model.FoodItem;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoodItemEventTest {

  private FoodItemEvent systemUnderTest;
  private Item testItem;
  private Player testPlayer;

  @Before
  public void setUp() throws Exception {
    systemUnderTest = new FoodItemEvent();
    testItem = new FoodItem();
    testPlayer = new Player();
  }

  @Test
  public void ignoreNonFoodItems() {
    testItem = new DrinkItem();
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void hungerValueGetsReducedByItemValue() {
    ((FoodItem) testItem).setValue(3);
    testPlayer.setHunger(5);
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(true, actual);
    Integer expected = 2;
    Assert.assertEquals(expected, testPlayer.getHunger());
  }

  @Test
  public void hungerValueDoesntFallBelowZero() {
    ((FoodItem) testItem).setValue(10);
    testPlayer.setHunger(5);
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(true, actual);
    Integer expected = 0;
    Assert.assertEquals(expected, testPlayer.getHunger());
  }

}