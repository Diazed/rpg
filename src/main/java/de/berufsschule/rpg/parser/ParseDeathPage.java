package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseDeathPage extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#DEATHPAGE")){
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        String deathPage = getStringBetweenQuotationMarks(line);
        gamePlan.setDeathPage(deathPage);
        return true;
      }
    }
    return false;
  }
}
