package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.parser.parenttests.GamePlanParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class ParseRoundHungerTest extends GamePlanParserTest<ParseRoundHunger> {

    @Test
    public void roundHungerIsSet() {
        Integer testRoundHunger = 3;
        when(parseModelMock.getAndSetNextLine()).thenReturn(Optional.of(testRoundHunger + " "));
        boolean returnValue = systemUnderTest.parseGamePlan(parseModelMock);

        assertThat(returnValue).isTrue();
        assertThat(gamePlan.getRoundHunger()).isEqualTo(testRoundHunger);
    }

    @Override
    protected ParseRoundHunger getSystemUnderTest() {
        return new ParseRoundHunger();
    }

    @Override
    protected Command getParsedCommand() {
        return Command.ROUNDHUNGER;
    }
}