package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseHealthTest extends PageParserTest<ParseHealth> {

  @Override
  protected ParseHealth getSystemUnderTest() {
    return new ParseHealth();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.HEALTH;
  }

  @Test
  public void healthIsSet() {

    int testHealthManipulation = 12;
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of("" + testHealthManipulation));

    boolean returnValue = systemUnderTest.parsePage(parseModel);

    assertThat(returnValue).isTrue();
    assertThat(page.getHealthManipulation()).isEqualTo(testHealthManipulation);

  }
}