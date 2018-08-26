package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.parser.GamePlanParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ParseGameNameTestGamePlan extends GamePlanParserTest<ParseGameName> {

    @Test
    public void gameNameIsSet() {

        final String nameString = "TestName";
        when(parseModelMock.getAndSetNextLine()).thenReturn(nameString);
        boolean returnValue = systemUnderTest.parseGamePlan(parseModelMock);
        assertThat(returnValue).isTrue();
        assertThat(gamePlan.getName()).isEqualTo(nameString);
    }

    @Override
    protected ParseGameName getSystemUnderTest() {
        return new ParseGameName();
    }

    @Override
    protected Command getParsedCommand() {
        return Command.GAMENAME;
    }
}