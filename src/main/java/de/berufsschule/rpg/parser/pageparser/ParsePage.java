package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.ArrayList;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParsePage extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#PAGE")){
      Page page = new Page();
      line = getNextLine(fileIn);
      page.setName(line);
      page.setDecisions(new ArrayList<>());
      page.setItems(new ArrayList<>());
      page.setSkills(new ArrayList<>());
      gamePlan.getPages().add(page);
      return true;
    }
    return false;
  }
}
