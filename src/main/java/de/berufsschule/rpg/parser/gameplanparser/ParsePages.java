package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import de.berufsschule.rpg.services.PageService;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParsePages extends BaseParser implements GamePlanParser {

  private List<PageParser> pageParsers;
  private PageService pageService;

  @Autowired
  public ParsePages(List<PageParser> pageParsers,
      PageService pageService) {
    this.pageParsers = pageParsers;
    this.pageService = pageService;
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
      pageService.savePage(getLastCreatedPage(gamePlan));
      return true;
    }
    return false;
  }
}
