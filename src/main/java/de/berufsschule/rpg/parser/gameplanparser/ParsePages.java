package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ParsePages implements GamePlanParser {

  private List<PageParser> pageParsers;

  @Autowired
  public ParsePages(List<PageParser> pageParsers) {
    this.pageParsers = pageParsers;
  }

  @Override
  public boolean parseGamePlan(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#PAGES")) {

      while (!line.contains("#ENDPAGES")) {

        if (fileIn.hasNextLine()) {
          line = fileIn.nextLine();
          if (!line.startsWith("//") && !Objects.equals(line, "")) {
            for (PageParser parser : pageParsers) {

              if (parser.parsePage(gamePlan, line, fileIn))
                break;
            }
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
