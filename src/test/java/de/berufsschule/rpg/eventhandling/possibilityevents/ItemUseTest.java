package de.berufsschule.rpg.eventhandling.possibilityevents;

import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
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
  private Possibility testPossibility;
  private Player testPlayer;
  private Page testPage;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    systemUnderTest = new ItemUse(itemService);
    testPossibility = new Possibility();
    testPlayer = new Player();
    testPage = new Page();
  }

  @Test
  public void returnFalseWhenPossibilityDoesNotUseItem() {
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenPlayerDoesNotOwnItem() {
    testPossibility.setUsedItem("axe");
    testPlayer.getItems().add("shovel");
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setTheUsedItemInTheJumpPageWhenPlayerDoesOwnItem() {
    testPossibility.setUsedItem("axe");
    testPlayer.getItems().add("axe");
    boolean actual = systemUnderTest.event(testPossibility, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("axe", testPage.getUsedItem());
  }
}