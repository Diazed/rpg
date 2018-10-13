package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseGiveItemTest extends PageParserTest<ParseGiveItem> {

  private static final Item ITEM_ONE = new Item();
  private static final Item ITEM_TWO = new Item();
  private static final Item ITEM_THREE = new Item();
  private static final String ITEM_ONE_NAME = "Item One";
  private static final String ITEM_TWO_NAME = "Item Two";
  private static final String ITEM_THREE_NAME = "Item";

  @Override
  protected ParseGiveItem getSystemUnderTest() {
    return new ParseGiveItem();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.GIVE;
  }

  @Test
  public void shouldAddItems() {

    when(parseModel.hasNextLine()).thenReturn(true);
    when(parseModel.getAndSetNextLine())
        .thenReturn(Optional.of(ITEM_TWO_NAME), Optional.of(ITEM_ONE_NAME),
            Optional.of("Stop the loop with # that Hashtag"));
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isTrue();
    assertThat(page.getItems()).contains(ITEM_ONE, ITEM_TWO);
    assertThat(page.getItems()).doesNotContain(ITEM_THREE);
  }

  @Test
  public void shouldStopAtLineEnd() {

    when(parseModel.hasNextLine()).thenReturn(false);
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isTrue();
    assertThat(page.getItems()).doesNotContain(ITEM_THREE, ITEM_ONE, ITEM_TWO);
  }

  @Test
  public void shouldHandleEmptyOptional() {

    when(parseModel.hasNextLine()).thenReturn(true);
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.empty());
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isTrue();
    assertThat(page.getItems()).doesNotContain(ITEM_THREE, ITEM_ONE, ITEM_TWO);
  }

  @Override
  protected GamePlan getGamePlan() {

    GamePlan modifiedGamePlan = super.getGamePlan();
    /*Simulate Items inside the GamePlan*/
    ITEM_ONE.setName(ITEM_ONE_NAME);
    ITEM_TWO.setName(ITEM_TWO_NAME);
    ITEM_THREE.setName(ITEM_THREE_NAME);
    modifiedGamePlan.getItems().add(ITEM_ONE);
    modifiedGamePlan.getItems().add(ITEM_TWO);
    modifiedGamePlan.getItems().add(ITEM_THREE);
    return modifiedGamePlan;
  }
}