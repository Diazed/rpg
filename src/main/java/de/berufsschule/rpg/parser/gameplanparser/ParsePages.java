package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.parser.tools.MissingIdHandler;
import de.berufsschule.rpg.parser.tools.QuestionPageHandler;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import de.berufsschule.rpg.services.PageService;
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
  private MissingIdHandler missingIdHandler;
  private QuestionPageHandler questionPageHandler;


  @Autowired
  public ParsePages(List<PageParser> pageParsers,
      PageService pageService, MissingIdHandler missingIdHandler,
      QuestionPageHandler questionPageHandler) {
    this.pageParsers = pageParsers;
    this.pageService = pageService;
    this.missingIdHandler = missingIdHandler;
    this.questionPageHandler = questionPageHandler;
  }

  @Override
  public boolean parseGamePlan(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.PAGES)) {
      while (!checkCommand(parseModel, Command.ENDPAGES)) {
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

      missingIdHandler.fillMissingJumpIds(pageService, parseModel);
      parseModel.setGamePlan(questionPageHandler.createQuestionPages(parseModel.getGamePlan()));

      return true;
    }
    return false;
  }
}
