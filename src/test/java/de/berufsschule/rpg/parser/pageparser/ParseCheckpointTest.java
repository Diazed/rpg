package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import org.junit.Test;

public class ParseCheckpointTest extends PageParserTest<ParseCheckpoint> {

  @Override
  protected ParseCheckpoint getSystemUnderTest() {
    return new ParseCheckpoint();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.CHECKPOINT;
  }

  @Test
  public void checkpointIsSet() {

    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isTrue();
    assertThat(page.isCheckpoint()).isTrue();
  }
}