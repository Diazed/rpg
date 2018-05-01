package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.services.ItemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemUseTest {

  @Mock
  private ItemService itemService;

  private ItemUse systemUnderTest;
  private Decision testDecision;
  private Player testPlayer;
  private Page testPage;
  private Item shovelItem;
  private Item axeItem;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    systemUnderTest = new ItemUse(itemService);
    testDecision = new Decision();
    testPlayer = new Player();
    testPage = new Page();
    shovelItem = new Item();
    axeItem = new Item();
    shovelItem.setName("shovel");
    axeItem.setName("axe");
  }

  @Test
  public void returnFalseWhenPossibilityDoesNotUseItem() {
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenPlayerDoesNotOwnItem() {
    testDecision.setUsedItem("axe");
    testPlayer.getItems().add(shovelItem);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setTheUsedItemInTheJumpPageWhenPlayerDoesOwnItem() {
    testDecision.setUsedItem("axe");
    testPlayer.getItems().add(axeItem);
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("axe", testPage.getUsedItem());
  }
}