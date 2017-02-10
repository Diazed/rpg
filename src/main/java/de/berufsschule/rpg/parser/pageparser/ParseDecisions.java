package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.pageparser.decisionparser.DecisionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ParseDecisions implements PageParser {
  private List<DecisionParser> decisionParsers;

  @Autowired
  public ParseDecisions(List<DecisionParser> decisionParsers) {
    this.decisionParsers = decisionParsers;
  }


  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("DECISIONS")) {

      while (!line.contains("#ENDDECISIONS")) {

        if (fileIn.hasNextLine()) {
          line = fileIn.nextLine();
          for (DecisionParser parser : decisionParsers) {

            if (parser.parseDecision(gamePlan, line, fileIn))
              break;
          }
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
}
