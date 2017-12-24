package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.services.PageService;
import java.util.Scanner;
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
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#PAGE")){

      Page lastPage = getLastCreatedPage(gamePlan);

      if (lastPage != null) {
        pageService.savePage(lastPage);
      }

      Page page = new Page();
      String name = getNextLine(fileIn);
      page.setName(name);
      gamePlan.getPages().add(page);
      return true;
    }
    return false;
  }
}
