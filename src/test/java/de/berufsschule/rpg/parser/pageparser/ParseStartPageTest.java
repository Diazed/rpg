package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.PageService;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class ParseStartPageTest extends PageParserTest<ParseStartPage> {

  private PageService pageService = mock(PageService.class);
  private static final int TEST_ID = 7;

  @Override
  protected ParseStartPage getSystemUnderTest() {
    return new ParseStartPage(pageService);
  }

  @Override
  protected Command getParsedCommand() {
    return Command.STARTPAGE;
  }

  @Test
  public void pageIsSavedAndSetAsStartpage() {

    boolean returnValue = systemUnderTest.parsePage(parseModel);
    Mockito.verify(pageService).savePage(page);
    assertThat(gamePlan.getStartPage()).isEqualTo(TEST_ID);
    assertThat(returnValue).isTrue();
  }

  @Override
  protected Page getPage() {
    Page manipulatedPage = super.getPage();

    manipulatedPage.setId(TEST_ID);

    return manipulatedPage;
  }
}