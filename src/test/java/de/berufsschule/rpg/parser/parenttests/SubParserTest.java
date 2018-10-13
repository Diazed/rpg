package de.berufsschule.rpg.parser.parenttests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class SubParserTest<P, SP, S> {

  protected P systemUnderTest;

  @Mock
  protected ParseModel parseModel;
  @Mock
  protected GamePlan gamePlan;

  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    when(parseModel.getCurrentLine())
        .thenReturn(getStartCommand().getCommand(), "", "", getEndCommand().getCommand());

    when(parseModel.getAndSetNextLine()).thenReturn(Optional.of("ValueDoesntMatter"));

    when(parseModel.getGamePlan()).thenReturn(gamePlan);

    //Return new Lists for save last.
    when(gamePlan.getItems()).thenReturn(new ArrayList<>());
    when(gamePlan.getSkills()).thenReturn(new ArrayList<>());
    when(gamePlan.getPages()).thenReturn(Collections.singletonList(new Page()));

    systemUnderTest = createSystemUnderTest(createSubParserMockList(), createService());

  }

  protected abstract P createSystemUnderTest(List<SP> subParser, S service);

  protected abstract List<SP> createSubParserMockList();

  protected abstract S createService();

  protected abstract Command getStartCommand();

  protected abstract Command getEndCommand();

  protected abstract boolean runParser(P systemUnderTest, ParseModel parseModel);

  @Test
  public void returnFalseWhenNotCorrectCommand() {
    when(parseModel.getCurrentLine())
        .thenReturn("");
    boolean returnValue = runParser(systemUnderTest, parseModel);
    assertThat(returnValue).isFalse();
  }

  @Test
  public void returnFalseWhenTheresNoNextLine() {
    // given: the parse model doesn't return the next line
    when(parseModel.getAndSetNextLine()).thenReturn(Optional.empty());
    // when: the parser asks for the next line to parse
    boolean returnValue = runParser(systemUnderTest, parseModel);
    // then: the parser exits with false
    assertThat(returnValue).isFalse();
    // and no item parser got executed
    verifyZeroInteractions(createSubParserMockList().get(0));
    verifyZeroInteractions(createSubParserMockList().get(1));
  }

}
