package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseExperienceTest extends PageParserTest<ParseExperience> {

  @Override
  protected ParseExperience getSystemUnderTest() {
    return new ParseExperience();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.XP;
  }

  @Test
  public void xpManipulationIsSet() {

    int testValue = 10;

    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of("" + testValue));
    boolean returnValue = systemUnderTest.parsePage(parseModel);

    assertThat(returnValue).isTrue();
    assertThat(page.getXpManipulation()).isEqualTo(testValue);

  }
}