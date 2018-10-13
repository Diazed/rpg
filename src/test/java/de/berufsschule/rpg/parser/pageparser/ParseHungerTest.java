package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseHungerTest extends PageParserTest<ParseHunger> {

  @Override
  protected ParseHunger getSystemUnderTest() {
    return new ParseHunger();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.HUNGER;
  }

  @Test
  public void hungerIsSet() {

    int testHungerManipulation = 12;
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of("" + testHungerManipulation));

    boolean returnValue = systemUnderTest.parsePage(parseModel);

    assertThat(returnValue).isTrue();
    assertThat(page.getHungerManipulation()).isEqualTo(testHungerManipulation);

  }
}