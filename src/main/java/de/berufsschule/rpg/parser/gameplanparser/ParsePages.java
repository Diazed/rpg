package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
          String nextLine = fileIn.nextLine();
          if (!nextLine.startsWith("//") && !Objects.equals(nextLine, "")) {
            for (PageParser parser : pageParsers) {

              if (parser.parsePage(gamePlan, nextLine, fileIn))
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
