package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseAltGoTo extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.ALTGOTO)) {

      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();

      Decision decision = getLastCreatedDecision(parseModel.getGamePlan());
      if (decision.getIsQuestion()) {
        return true;
      }

      if (optionalNextLine.isPresent()) {
        String line = optionalNextLine.get();
        Page pageByName = findPageByName(parseModel.getGamePlan(), line);
        if (pageByName == null) {
          parseModel.getUncompleteDecisions().add(decision);
        } else {
          decision.setAltJump(pageByName.getId());
        }
        decision.setAltJumpName(line);
      }
      return true;
    }
    return false;
  }
}
