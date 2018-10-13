package de.berufsschule.rpg.parser.gameplanparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import de.berufsschule.rpg.parser.pageparser.ParseHealth;
import de.berufsschule.rpg.parser.pageparser.ParseHunger;
import de.berufsschule.rpg.parser.parenttests.SubParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.parser.tools.MissingIdHandler;
import de.berufsschule.rpg.parser.tools.QuestionPageHandler;
import de.berufsschule.rpg.services.PageService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ParsePagesTest extends SubParserTest<ParsePages, PageParser, PageService> {

  @Mock
  private ParseHealth parseHealth;
  @Mock
  private ParseHunger parseHunger;
  @Mock
  private PageService pageService;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    when(parseHunger.parsePage(parseModel)).thenReturn(true);
  }

  @Override
  protected ParsePages createSystemUnderTest(List<PageParser> subParser, PageService service) {
    return new ParsePages(subParser, service, mock(MissingIdHandler.class), mock(
        QuestionPageHandler.class));
  }

  @Override
  protected List<PageParser> createSubParserMockList() {
    return Arrays.asList(parseHealth, parseHunger);
  }

  @Override
  protected PageService createService() {
    return pageService;
  }

  @Override
  protected Command getStartCommand() {
    return Command.PAGES;
  }

  @Override
  protected Command getEndCommand() {
    return Command.ENDPAGES;
  }

  @Override
  protected boolean runParser(ParsePages systemUnderTest, ParseModel parseModel) {
    return systemUnderTest.parseGamePlan(parseModel);
  }

  @Test
  public void runsAllPageParser() {

    boolean returnValue = systemUnderTest.parseGamePlan(parseModel);

    verify(parseHealth, atLeastOnce()).parsePage(parseModel);
    verify(parseHunger, atLeastOnce()).parsePage(parseModel);
    assertThat(returnValue).isTrue();
  }

  @Test
  public void lastCreatedPageIsSaved() {
    Page pageToSave = new Page();
    when(gamePlan.getPages()).thenReturn(Collections.singletonList(pageToSave));
    boolean returnValue = systemUnderTest.parseGamePlan(parseModel);
    verify(pageService).savePage(pageToSave);
    assertThat(returnValue).isTrue();
  }
}