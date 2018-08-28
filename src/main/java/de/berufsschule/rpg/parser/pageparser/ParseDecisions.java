package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.DecisionParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.DecisionService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ParseDecisions extends BaseParser implements PageParser {

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
    if (checkCommand(parseModel, Command.DECISIONS)) {
      while (!checkCommand(parseModel, Command.ENDDECISIONS)) {

        Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
        if (optionalNextLine.isPresent()){
          String line = optionalNextLine.get();
          if (!line.startsWith("//") && !Objects.equals(line, "")) {
            for (DecisionParser parser : decisionParsers) {
              if (parser.parseDecision(parseModel)) {
                break;
              }
            }
          }
        }
      }
      saveLastCreatedDecision(parseModel);
      return true;
    }
    return false;
  }

  private void saveLastCreatedDecision(ParseModel parseModel) {
    Decision lastCreatedDecision = getLastCreatedDecision(parseModel.getGamePlan());
    if (lastCreatedDecision != null) {
      decisionService.saveDecision(lastCreatedDecision);
    }
  }
}
