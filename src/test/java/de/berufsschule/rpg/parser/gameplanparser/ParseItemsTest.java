package de.berufsschule.rpg.parser.gameplanparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.itemparser.ItemParser;
import de.berufsschule.rpg.parser.itemparser.ParseItemDescription;
import de.berufsschule.rpg.parser.itemparser.ParseItemName;
import de.berufsschule.rpg.parser.parenttests.SubParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.ItemService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ParseItemsTest extends SubParserTest<ParseItems, ItemParser, ItemService> {

  @Mock
  private ItemService itemService;
  @Mock
  private ParseItemName parseItemName;
  @Mock
  private ParseItemDescription parseItemDescription;


  @Before
  public void setUp() throws Exception {

    super.setUp();
    when(parseItemDescription.parseItem(parseModel)).thenReturn(true);
  }

  @Override
  protected ParseItems createSystemUnderTest(List<ItemParser> subParser, ItemService service) {
    return new ParseItems(subParser, itemService);
  }

  @Override
  protected List<ItemParser> createSubParserMockList() {
    return Arrays.asList(parseItemName, parseItemDescription);
  }

  @Override
  protected ItemService createService() {
    return itemService;
  }

  @Override
  protected Command getStartCommand() {
    return Command.ITEMS;
  }

  @Override
  protected Command getEndCommand() {
    return Command.ENDITEMS;
  }

  @Override
  protected boolean runParser(ParseItems systemUnderTest, ParseModel parseModel) {
    return systemUnderTest.parseGamePlan(parseModel);
  }

  @Test
  public void runsAllItemParser() {

    boolean returnValue = runParser(systemUnderTest, parseModel);

    verify(parseItemName, atLeastOnce()).parseItem(parseModel);
    verify(parseItemDescription, atLeastOnce()).parseItem(parseModel);
    assertThat(returnValue).isTrue();
  }

  @Test
  public void lastCreatedItemIsSaved() {
    Item itemToSave = new Item();
    when(gamePlan.getItems()).thenReturn(Collections.singletonList(itemToSave));
    boolean returnValue = systemUnderTest.parseGamePlan(parseModel);
    verify(itemService).saveItem(itemToSave);
    assertThat(returnValue).isTrue();
  }
}