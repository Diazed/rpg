package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.SubParserRunner;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.DecisionParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.DecisionService;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ParseDecisions extends SubParserRunner<DecisionParser> implements PageParser {

  private List<DecisionParser> decisionParsers;
  private DecisionService decisionService;

  @Autowired
  public ParseDecisions(List<DecisionParser> decisionParsers,
      DecisionService decisionService) {
    this.decisionParsers = decisionParsers;
    this.decisionService = decisionService;
  }


  @Override
  public boolean parsePage(ParseModel parseModel) {
    return parse(parseModel);
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
  protected BiFunction<DecisionParser, ParseModel, Boolean> getParseMethod() {
    return DecisionParser::parseDecision;
  }

  @Override
  protected List<DecisionParser> getSubParser() {
    return decisionParsers;
  }

  @Override
  protected void saveLastCreated(ParseModel parseModel) {
    Decision lastCreatedDecision = getLastCreatedDecision(parseModel.getGamePlan());
    if (lastCreatedDecision != null) {
      decisionService.saveDecision(lastCreatedDecision);
    }
  }
}
