package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ParsePage extends BaseParser {
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {

    if (line.contains("#PAGE")){
      Page page = new Page();
      List<Decision> decisions = new ArrayList<Decision>();
      List<Item> items = new ArrayList<Item>();
      page.setDecisions(decisions);
      page.setItems(items);
      gamePlan.getPages().add(page);
      return true;
    }
    return false;

  }
}
