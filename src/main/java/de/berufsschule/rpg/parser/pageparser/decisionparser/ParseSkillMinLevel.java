package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseSkillMinLevel extends BaseParser implements DecisionParser {

  @Override
  public boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("SKILLMINLVL")) {
      Decision decision = getLastCreatedDecision(gamePlan);
      decision.setSkillMinLvl(parseInt(getNextLine(fileIn)));
      return true;
    }
    return false;
  }
}
