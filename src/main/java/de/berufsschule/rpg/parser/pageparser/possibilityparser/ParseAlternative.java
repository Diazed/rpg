package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Question;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseAlternative extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#ALTERNATIVE")) {
      line = getNextLine(fileIn);
      Possibility possibility = getLastCreatedPossibility(gamePlan);
      if (possibility.getClass() == Decision.class) {
        Decision decision = (Decision) possibility;
        decision.setAltJump(findPageByName(gamePlan, line).getId());
        return true;
      } else if (possibility.getClass() == Question.class) {
        Question question = (Question) possibility;
        question.setAltAnswer(line);
      }
    }
    return false;
  }
}
