package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.PageService;
import org.junit.Test;

public class ParseDeathPageTest extends PageParserTest<ParseDeathPage> {

  private static final Integer TEST_ID = 2;

  @Override
  protected ParseDeathPage getSystemUnderTest() {
    return new ParseDeathPage(mock(PageService.class));
  }

  @Override
  protected Command getParsedCommand() {
    return Command.DEATHPAGE;
  }

  @Test
  public void deathPageIsSet() {

    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isTrue();
    assertThat(gamePlan.getDeathPage()).isEqualTo(TEST_ID);

  }

  @Override
  protected Page getPage() {
    Page page = super.getPage();
    page.setId(TEST_ID);
    return page;
  }
}