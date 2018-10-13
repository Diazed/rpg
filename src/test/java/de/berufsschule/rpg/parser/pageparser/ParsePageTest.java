package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.PageService;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ParsePageTest extends PageParserTest<ParsePage> {

  private PageService pageService = mock(PageService.class);

  @Override
  protected ParsePage getSystemUnderTest() {
    return new ParsePage(pageService);
  }

  @Override
  protected Command getParsedCommand() {
    return Command.PAGE;
  }

  @Test
  public void lastCreatedPageIsSaved() {

    when(parseModel.getAndSetNextLine()).thenReturn(Optional.empty());
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    Mockito.verify(pageService).savePage(page);
    assertThat(returnValue).isTrue();
  }

  @Test
  public void pageNameIsAddedForTheNamePage() {

    String testName = "Page";
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of(testName));
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(gamePlan.getPages().get(1).getName()).isEqualTo(testName);
    assertThat(returnValue).isTrue();
  }
}