package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.ParseModel;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Question;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseAlternative extends BaseParser implements PossibilityParser {

  @Override
  public boolean parsePossibility(ParseModel parseModel) {
    if (parseModel.getLine().contains("#ALTERNATIVE")) {

      Possibility possibility = getLastCreatedPossibility(parseModel.getGamePlan());
      String line = parseModel.getAndSetNextLine();
      if (possibility.getClass() == Decision.class) {

        Page pageByName = findPageByName(parseModel.getGamePlan(), line);
        Decision decision = (Decision) possibility;
        if (pageByName == null) {

          parseModel.getUncompleteDecisions().add(decision);
        } else {
          decision.setMainJump(pageByName.getId());
        }
        decision.setAltJumpName(line);
        return true;
      } else if (possibility.getClass() == Question.class) {

        Question question = (Question) possibility;
        question.setAltAnswer(line);
        return true;
      }
    }
    return false;
  }
}
