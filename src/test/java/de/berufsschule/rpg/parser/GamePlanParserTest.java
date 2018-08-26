package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.gameplanparser.GamePlanParser;
import de.berufsschule.rpg.parser.tools.Command;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.method.P;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class GamePlanParserTest<P extends GamePlanParser> {

    protected GamePlan gamePlan = new GamePlan();
    protected ParseModel parseModelMock = mock(ParseModel.class);

    protected P systemUnderTest = getSystemUnderTest();

    @Before
    public void setUp() throws Exception {
        gamePlan = new GamePlan();
        systemUnderTest = getSystemUnderTest();
        when(parseModelMock.getCurrentLine()).thenReturn(getParsedCommand().getCommand());
        when(parseModelMock.getGamePlan()).thenReturn(gamePlan);
    }

    protected abstract P getSystemUnderTest();

    protected abstract Command getParsedCommand();

    @Test
    public void returnFalseWhenWrongCommand() {
        when(parseModelMock.getCurrentLine()).thenReturn("SomeWrongCmd");

        boolean returnValue = systemUnderTest.parseGamePlan(parseModelMock);
        assertThat(returnValue).isFalse();
    }
}
