package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.DecisionParser;
import de.berufsschule.rpg.services.DecisionService;
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
    if (parseModel.getLine().contains("#DECISIONS")) {
      while (!parseModel.getLine().contains("#ENDDECISIONS")) {
        String line = parseModel.getAndSetNextLine();
        if (!line.startsWith("//") && !Objects.equals(line, "")) {
          for (DecisionParser parser : decisionParsers) {
            if (parser.parseDecision(parseModel)) {
              break;
            }
          }
        }
      }
      Decision lastCreatedDecision = getLastCreatedDecision(parseModel.getGamePlan());
      if (lastCreatedDecision != null) {
        decisionService.saveDecision(lastCreatedDecision);
      }
      return true;
    }
    return false;
  }
}
