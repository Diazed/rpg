package de.berufsschule.rpg.parser.pageparser;

import static de.berufsschule.rpg.parser.tools.Command.STORYTEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseStorytextTest extends PageParserTest {

  @Override
  protected PageParser getSystemUnderTest() {
    return new ParseStorytext();
  }

  @Override
  protected Command getParsedCommand() {
    return STORYTEXT;
  }

  @Test
  public void textIsSet() {

    String line1 = "test";
    String line2 = "test";
    String line3 = "#";
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of(line1), Optional.of(line2), Optional.of(line3));

    boolean returnValue = systemUnderTest.parsePage(parseModel);

    assertThat(returnValue).isTrue();
    assertThat(page.getStorytext()).isEqualTo("test test");


  }

  @Test
  public void longWordSplit() {

    String line1 = "12345678901234567890123456789012345";
    String line2 = "test";
    String line3 = "#";
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of(line1), Optional.of(line2), Optional.of(line3));

    boolean returnValue = systemUnderTest.parsePage(parseModel);

    assertThat(returnValue).isTrue();
    assertThat(page.getStorytext()).isEqualTo("1234567890123456789012345- 6789012345 test");


  }
}