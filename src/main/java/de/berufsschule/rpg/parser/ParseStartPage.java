package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseStartPage extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#STARTPAGE")){
      if (fileIn.hasNextLine()){
        line = fileIn.nextLine();
        String startPage = getStringBetweenQuotationMarks(line);
        gamePlan.setStartPage(startPage);
        return true;
      }
    }
    return false;
  }
}
