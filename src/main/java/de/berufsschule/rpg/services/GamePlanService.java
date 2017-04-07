package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.parser.ParserRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GamePlanService {

  ParserRunner parserRunner;

  @Autowired
  public GamePlanService(ParserRunner parserRunner) {
    this.parserRunner = parserRunner;
  }

  public List<GamePlan> getGamePlanList() {
    List<GamePlan> gamePlans = new ArrayList<>();
    for (GamePlan gamePlan : parserRunner.getGames().values()) {
      gamePlans.add(gamePlan);
    }
    return gamePlans;
  }

  public GamePlan getGamePlan(String gamePlanName) {
    return parserRunner.getGames().get(gamePlanName);
  }


  public HashMap<String, Item> getItemHashMapOfGamePlan(String gamePlanName) {
    GamePlan gamePlan = getGamePlan(gamePlanName);
    HashMap<String, Item> result = new HashMap<>();
    for (Item item : gamePlan.getItems()) {
      result.put(item.getName(), item);
    }
    return result;
  }

  public HashMap<String, Skill> getSkillHashMapOfGamePlan(String gamePlanName) {
    GamePlan gamePlan = getGamePlan(gamePlanName);
    HashMap<String, Skill> result = new HashMap<>();
    for (Skill skill : gamePlan.getSkills()) {
      result.put(skill.getName(), skill);
    }
    return result;
  }

  public HashMap<String, Page> getPageHashMapOfGamePlan(String gamePlanName) {
    GamePlan gamePlan = getGamePlan(gamePlanName);
    HashMap<String, Page> result = new HashMap<>();
    for (Page page : gamePlan.getPages()) {
      result.put(page.getName(), page);
    }
    return result;
  }

}
