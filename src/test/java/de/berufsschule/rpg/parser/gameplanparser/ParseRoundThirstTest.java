package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.parser.parenttests.GamePlanParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ParseRoundThirstTest extends GamePlanParserTest<ParseRoundThirst> {

    @Test
    public void roundThirstIsSet() {
        Integer testRoundThirst = 3;
        when(parseModelMock.getAndSetNextLine()).thenReturn(Optional.of(testRoundThirst + " "));
        boolean returnValue = systemUnderTest.parseGamePlan(parseModelMock);

        assertThat(returnValue).isTrue();
        assertThat(gamePlan.getRoundThirst()).isEqualTo(testRoundThirst);
    }

    @Override
    protected ParseRoundThirst getSystemUnderTest() {
        return new ParseRoundThirst();
    }

    @Override
    protected Command getParsedCommand() {
        return Command.ROUNDTHIRST;
    }
}