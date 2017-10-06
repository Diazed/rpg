package de.berufsschule.rpg.eventhandling.decisionevents;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
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

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    systemUnderTest = new ItemUse(itemService);
    testDecision = new Decision();
    testPlayer = new Player();
    testPage = new Page();
  }

  @Test
  public void returnFalseWhenDecisionDoesNotUseItem() {
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void returnFalseWhenPlayerDoesNotOwnItem() {
    testDecision.setUsedItem("axe");
    testPlayer.getItems().add("shovel");
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(false, actual);
  }

  @Test
  public void setTheUsedItemInTheJumpPageWhenPlayerDoesOwnItem() {
    testDecision.setUsedItem("axe");
    testPlayer.getItems().add("axe");
    boolean actual = systemUnderTest.event(testDecision, testPlayer, testPage);
    Assert.assertEquals(true, actual);
    Assert.assertEquals("axe", testPage.getUsedItem());
  }
}