package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Scanner;

@Component
public class ParsePage extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#PAGE")){
      Page page = new Page();
      page.setDecisions(new ArrayList<>());
      page.setItems(new ArrayList<>());
      gamePlan.getPages().add(page);
      return true;
    }
    return false;
  }
}
