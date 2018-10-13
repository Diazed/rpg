package de.berufsschule.rpg.parser.pageparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.DecisionParser;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.ParseAltAnswer;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.ParseAnswer;
import de.berufsschule.rpg.parser.parenttests.SubParserTest;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.DecisionService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.mockito.Mock;

public class ParseDecisionsTest extends
    SubParserTest<ParseDecisions, DecisionParser, DecisionService> {

  @Mock
  private DecisionService decisionService;
  @Mock
  private ParseAnswer parseAnswer;
  @Mock
  private ParseAltAnswer parseAltAnswer;

  @Override
  protected ParseDecisions createSystemUnderTest(List<DecisionParser> subParser,
      DecisionService service) {
    return new ParseDecisions(subParser, service);
  }

  @Override
  protected List<DecisionParser> createSubParserMockList() {
    return Arrays.asList(parseAltAnswer, parseAnswer);
  }

  @Override
  protected DecisionService createService() {
    return decisionService;
  }

  @Override
  protected Command getStartCommand() {
    return Command.DECISIONS;
  }

  @Override
  protected Command getEndCommand() {
    return Command.ENDDECISIONS;
  }

  @Override
  protected boolean runParser(ParseDecisions systemUnderTest, ParseModel parseModel) {
    return systemUnderTest.parsePage(parseModel);
  }

  @Test
  public void runsAllDecisionParser() {

    boolean returnValue = runParser(systemUnderTest, parseModel);

    verify(parseAltAnswer, atLeastOnce()).parseDecision(parseModel);
    verify(parseAnswer, atLeastOnce()).parseDecision(parseModel);
    assertThat(returnValue).isTrue();
  }

  @Test
  public void lastCreatedItemIsSaved() {
    Decision decisionToSave = new Decision();
    Page page = new Page();
    page.getDecisions().add(decisionToSave);
    when(gamePlan.getPages()).thenReturn(Collections.singletonList(page));
    boolean returnValue = systemUnderTest.parsePage(parseModel);
    verify(decisionService).saveDecision(decisionToSave);
    assertThat(returnValue).isTrue();
  }

}