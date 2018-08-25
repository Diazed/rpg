package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParseDeathPage extends BaseParser implements PageParser {

  private PageService pageService;

  @Autowired
  public ParseDeathPage(PageService pageService) {
    this.pageService = pageService;
  }

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.DEATHPAGE)) {
      Page lastPage = getLastCreatedPage(parseModel.getGamePlan());
      pageService.savePage(lastPage);
      parseModel.getGamePlan().setDeathPage(lastPage.getId());
      return true;
    }
    return false;
  }
}
