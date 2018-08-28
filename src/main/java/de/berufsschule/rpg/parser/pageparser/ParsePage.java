package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.PageService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParsePage extends BaseParser implements PageParser {

  private PageService pageService;

  @Autowired
  public ParsePage(PageService pageService) {
    this.pageService = pageService;
  }

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.PAGE)) {

      saveLastCreatedPage(parseModel);

      Page page = new Page();
      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      optionalNextLine.ifPresent(page::setName);
      parseModel.getGamePlan().getPages().add(page);
      return true;
    }
    return false;
  }

  private void saveLastCreatedPage(ParseModel parseModel) {
    Page lastCreatedPage = getLastCreatedPage(parseModel.getGamePlan());
    if (lastCreatedPage != null) {
      pageService.savePage(lastCreatedPage);
    }
  }
}
