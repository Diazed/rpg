package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.parser.parenttests.PageParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

public class ParseAddSkillpointsTest extends PageParserTest<ParseAddSkillpoints> {

  @Override
  protected ParseAddSkillpoints getSystemUnderTest() {
    return new ParseAddSkillpoints();
  }

  @Override
  protected Command getParsedCommand() {
    return Command.ADDSKILLPOINTS;
  }

  @Test
  public void skillPointsAreAdded() {

    final String nameString = "3";
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of(nameString));
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    assertThat(returnValue).isTrue();
    assertThat(page.getSkillPointManipulation()).isEqualTo(3);

  }
}