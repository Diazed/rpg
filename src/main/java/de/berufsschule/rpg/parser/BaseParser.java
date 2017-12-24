package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Skill;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class BaseParser {

  private static Integer possibilityIdCounter;

  protected Page findPageByName(GamePlan gamePlan, String pagename) {
    for (Page page : gamePlan.getPages()) {
      if (page.getName().equals(pagename)) {
      }
      return page;
    }
    return null;
  }

  protected Skill findSkillByName(GamePlan gamePlan, String skillname) {
    for (Skill skill : gamePlan.getSkills()) {
      if (skill.getName().equals(skillname)) {
      }
      return skill;
    }
    return null;
  }

  protected Item findItemByName(GamePlan gamePlan, String itemname) {
    for (Item item : gamePlan.getItems()) {
      if (item.getName().equals(itemname)) {
      }
      return item;
    }
    return null;
  }

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

  protected Possibility getLastCreatedPossibility(GamePlan gamePlan) {
    List<Possibility> possibilities = getLastCreatedPage(gamePlan).getPossibilities();
    return possibilities.get(possibilities.size() - 1);
  }

  public Integer parseInt(String line) {
    line = line.trim();
    Integer content = 1;
    try {
      content = Integer.parseInt(line);
    } catch (Exception e) {
      log.error("Could not parse \"" + line + "\" into a Integer. Error: " + e.getMessage());
    }
    return content;
  }

}
