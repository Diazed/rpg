package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public abstract class BaseParser {

  public String getNextLine(Scanner fileIn) {
    if (fileIn.hasNextLine())
      return getStringBetweenQuotationMarks(fileIn.nextLine());
    return "";
  }

  public String getStringBetweenQuotationMarks(String line) {
    line = line.replace("\t", "");
    line = line.trim();

    Pattern pattern = Pattern.compile("'(.*?)'");
    Matcher matcher = pattern.matcher(line);
    if (matcher.find())
      line = matcher.group(1);
    return line;
  }

  public Page getLastCreatedPage(GamePlan gamePlan) {
    HashMap<String, Integer> indexes = getIndexes(gamePlan);
    return gamePlan.getPages().get(indexes.get("pageIndx"));
  }

  public Item getLastCreatedItem(GamePlan gamePlan) {
    HashMap<String, Integer> indexes = getIndexes(gamePlan);
    return gamePlan.getItems().get(indexes.get("itemIndx"));
  }

  public Decision getLastCreatedDecision(GamePlan gamePlan) {
    HashMap<String, Integer> indexes = getIndexes(gamePlan);
    return getLastCreatedPage(gamePlan).getDecisions().get(indexes.get("decisionIndx"));
  }

  public HashMap<String, Integer> getIndexes(GamePlan gamePlan){

    HashMap<String, Integer> indexes = new HashMap<>();
    indexes.put("pageIndx", gamePlan.getPages().size() - 1);
    indexes.put("itemIndx", gamePlan.getItems().size() - 1);
    indexes.put("decisionIndx", gamePlan.getPages().get(indexes.get("pageIndx")).getDecisions().size() - 1);

    return indexes;
  }

  public Integer parseInt(String line) {
    line = line.trim();
    return Integer.parseInt(line);
  }

}
