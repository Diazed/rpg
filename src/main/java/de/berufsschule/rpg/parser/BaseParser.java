package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.services.PossibilityService;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class BaseParser {


  private static List<Page> pagesToCheck = new LinkedList<>();


  protected Page findPageByName(GamePlan gamePlan, String pagename) {
    for (Page page : gamePlan.getPages()) {
      if (page.getName().equals(pagename)) {
        return page;
      }
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



  protected Page getLastCreatedPage(GamePlan gamePlan) {
    Integer size = gamePlan.getPages().size();
    if (size >= 1) {
      return gamePlan.getPages().get(gamePlan.getPages().size() - 1);
    } else {
      return null;
    }
  }

  protected Item getLastCreatedItem(GamePlan gamePlan) {
    Integer size = gamePlan.getItems().size();
    if (size >= 1) {
      return gamePlan.getItems().get(gamePlan.getItems().size() - 1);
    } else {
      return null;
    }
  }

  public Skill getLastCreatedSkill(GamePlan gamePlan){
    Integer size = gamePlan.getSkills().size();
    if (size >= 1) {
      return gamePlan.getSkills().get(size - 1);
    } else {
      return null;
    }
  }

  protected Possibility getLastCreatedPossibility(GamePlan gamePlan) {

    List<Possibility> possibilities = getLastCreatedPage(gamePlan).getPossibilities();

    Integer size = possibilities.size();
    if (size >= 1) {
      return possibilities.get(size - 1);
    } else {
      return null;
    }
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
