package de.berufsschule.rpg.eventhandling.itemevents;

import de.berufsschule.rpg.domain.model.DrinkItem;
import de.berufsschule.rpg.domain.model.HealItem;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HealItemEventTest {

  private HealItemEvent systemUnderTest;
  private Item testItem;
  private Player testPlayer;

  @Before
  public void setUp() {
    systemUnderTest = new HealItemEvent();
    testItem = new HealItem();
    testPlayer = new Player();
  }

  @Test
  public void ignoreNonHealItems() {
    testItem = new DrinkItem();
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void HealthValueGetsIncreasedByItemValue() {
    ((HealItem) testItem).setValue(3);
    testPlayer.setHitpoints(5);
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(true, actual);
    Integer expected = 8;
    Assert.assertEquals(expected, testPlayer.getHitpoints());
  }

  @Test
  public void healthValueDoesntExceedHundret() {
    ((HealItem) testItem).setValue(10);
    testPlayer.setHitpoints(95);
    boolean actual = systemUnderTest.event(testItem, testPlayer);
    Assert.assertEquals(true, actual);
    Integer expected = 100;
    Assert.assertEquals(expected, testPlayer.getHitpoints());
  }

}