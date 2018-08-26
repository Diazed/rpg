package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.*;

import java.util.List;

import de.berufsschule.rpg.parser.tools.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public abstract class BaseParser {

  protected boolean checkCommand(ParseModel parseModel, Command command){
    return parseModel.getCurrentLine().contains(command.getCommand());
  }

  // TODO: Use Optionals
  protected Page findPageByName(GamePlan gamePlan, String pagename) {
    for (Page page : gamePlan.getPages()) {
      if (page.getName().equals(pagename)) {
        return page;
      }
    }
    return null;
  }

  // TODO: Use Optionals
  protected Skill findSkillByName(GamePlan gamePlan, String skillname) {
    for (Skill skill : gamePlan.getSkills()) {
      if (skill.getName().equals(skillname)) {
        return skill;
      }
    }
    return null;
  }

  // TODO: Use Optionals
  protected Item findItemByName(GamePlan gamePlan, String itemname) {
    for (Item item : gamePlan.getItems()) {
      if (item.getName().equals(itemname)) {
        return item;
      }
    }
    return null;
  }

  protected Page getLastCreatedPage(GamePlan gamePlan) {
    int size = gamePlan.getPages().size();
    if (size >= 1) {
      return gamePlan.getPages().get(gamePlan.getPages().size() - 1);
    } else {
      return null;
    }
  }

  protected Item getLastCreatedItem(GamePlan gamePlan) {
    int size = gamePlan.getItems().size();
    if (size >= 1) {
      return gamePlan.getItems().get(gamePlan.getItems().size() - 1);
    } else {
      return null;
    }
  }

  public Skill getLastCreatedSkill(GamePlan gamePlan){
    int size = gamePlan.getSkills().size();
    if (size >= 1) {
      return gamePlan.getSkills().get(size - 1);
    } else {
      return null;
    }
  }

  protected Decision getLastCreatedDecision(GamePlan gamePlan) {

    List<Decision> decisions = getLastCreatedPage(gamePlan).getDecisions();

    int size = decisions.size();
    if (size >= 1) {
      return decisions.get(size - 1);
    } else {
      return null;
    }
  }

  public Integer parseInt(String line) {
    line = line.trim();
    int content = 1;
    try {
      content = Integer.parseInt(line);
    } catch (Exception e) {
      log.warn("Could not parse \"" + line + "\" into a Integer. Error: " + e.getMessage());
    }
    return content;
  }
}
