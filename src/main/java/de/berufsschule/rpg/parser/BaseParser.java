package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
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

  private String getStringBetweenQuotationMarks(String line) {
    line = line.replace("\t", "");
    line = line.trim();

    Pattern pattern = Pattern.compile("'(.*?)'");
    Matcher matcher = pattern.matcher(line);
    if (matcher.find())
      line = matcher.group(1);
    return line;
  }

  protected Page getLastCreatedPage(GamePlan gamePlan) {
    return gamePlan.getPages().get(gamePlan.getPages().size() - 1);
  }

  protected Item getLastCreatedItem(GamePlan gamePlan) {
    return gamePlan.getItems().get(gamePlan.getItems().size() - 1);
  }

  public Skill getLastCreatedSkill(GamePlan gamePlan){
    return gamePlan.getSkills().get(gamePlan.getSkills().size() - 1);
  }

  protected Decision getLastCreatedDecision(GamePlan gamePlan) {
    List<Decision> decisions = getLastCreatedPage(gamePlan).getDecisions();
    return decisions.get(decisions.size() - 1);
  }

  public Integer parseInt(String line) {
    line = line.trim();
    return Integer.parseInt(line);
  }

}
