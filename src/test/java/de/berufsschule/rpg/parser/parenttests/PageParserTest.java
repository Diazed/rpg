package de.berufsschule.rpg.parser.parenttests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.junit.Before;
import org.junit.Test;

public abstract class PageParserTest<P extends PageParser> {

  protected GamePlan gamePlan = new GamePlan();
  protected Page page = new Page();
  protected ParseModel parseModel = mock(ParseModel.class);

  protected P systemUnderTest = getSystemUnderTest();

  @Before
  public void setUp() throws Exception {
    gamePlan = getGamePlan();
    systemUnderTest = getSystemUnderTest();
    when(parseModel.getCurrentLine()).thenReturn(getParsedCommand().getCommand());
    when(parseModel.getGamePlan()).thenReturn(gamePlan);
  }

  protected abstract P getSystemUnderTest();

  protected abstract Command getParsedCommand();

  protected Page getPage(){
    return page;
  }

  protected GamePlan getGamePlan(){
    gamePlan = new GamePlan();
    gamePlan.getPages().add(getPage());
    return gamePlan;
  }

  @Test
  public void returnFalseWhenWrongCommand() {
    when(parseModel.getCurrentLine()).thenReturn("SomeWrongCmd");

    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isFalse();
  }
}
