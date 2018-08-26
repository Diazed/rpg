package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.parser.GamePlanParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ParseRoundExpTest extends GamePlanParserTest<ParseRoundExp> {

    @Test
    public void roundExpIsSet() {

        Integer testRoundExp = 4;
        when(parseModelMock.getAndSetNextLine()).thenReturn(testRoundExp + " ");
        boolean returnValue = systemUnderTest.parseGamePlan(parseModelMock);

        assertThat(returnValue).isTrue();
        assertThat(gamePlan.getRoundExp()).isEqualTo(testRoundExp);
    }

    @Override
    protected ParseRoundExp getSystemUnderTest() {
        return new ParseRoundExp();
    }

    @Override
    protected Command getParsedCommand() {
        return Command.ROUNDEXP;
    }
}