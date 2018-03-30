package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import de.berufsschule.rpg.services.PageService;
import de.berufsschule.rpg.services.PossibilityService;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ParsePages extends BaseParser implements GamePlanParser {

  private List<PageParser> pageParsers;
  private PageService pageService;
  private PossibilityService possibilityService;

  @Autowired
  public ParsePages(List<PageParser> pageParsers,
      PageService pageService, PossibilityService possibilityService) {
    this.pageParsers = pageParsers;
    this.pageService = pageService;
    this.possibilityService = possibilityService;
  }

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (parseModel.getLine().contains("#PAGES")) {

      while (!parseModel.getLine().contains("#ENDPAGES")) {

        if (parseModel.hasNextLine()) {
          String line = parseModel.getAndSetNextLine();
          if (!line.startsWith("//") && !Objects.equals(line, "")) {
            for (PageParser parser : pageParsers) {

              if (parser.parsePage(parseModel)) {
                break;
              }
            }
          }
        } else {
          return false;
        }
      }
      Page lastCreatedPage = getLastCreatedPage(parseModel.getGamePlan());
      if (lastCreatedPage != null) {
        pageService.savePage(lastCreatedPage);
      }

      for (Decision decision : parseModel.getUncompleteDecisions()) {
        if (decision.getMainJump() == null) {
          Page mainJump = findPageByName(parseModel.getGamePlan(), decision.getMainJumpName());
          if (mainJump == null) {
            log.warn("JumpPage " + decision.getMainJumpName() + " konnte nicht gefunden werden.");
          } else {
            decision.setMainJump(mainJump.getId());
            possibilityService.savePossibility(decision);
            pageService.savePage(mainJump);
          }
        }
        if (decision.getMainJump() == null) {
          Page altJump = findPageByName(parseModel.getGamePlan(), decision.getAltJumpName());
          if (altJump == null) {
            log.warn(
                "Alt JumpPage " + decision.getAltJumpName() + " konnte nicht gefunden werden.");
          } else {
            decision.setAltJump(altJump.getId());
            possibilityService.savePossibility(decision);
            pageService.savePage(altJump);
          }
        }


      }

      return true;
    }
    return false;
  }
}
