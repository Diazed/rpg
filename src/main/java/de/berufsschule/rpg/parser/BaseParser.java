package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Skill;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class BaseParser {

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
        return skill;
      }
    }
    return null;
  }

  protected Item findItemByName(GamePlan gamePlan, String itemname) {
    for (Item item : gamePlan.getItems()) {
      if (item.getName().equals(itemname)) {
        return item;
      }
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

  protected Decision getLastCreatedDecision(GamePlan gamePlan) {

    List<Decision> decisions = getLastCreatedPage(gamePlan).getDecisions();

    Integer size = decisions.size();
    if (size >= 1) {
      return decisions.get(size - 1);
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
