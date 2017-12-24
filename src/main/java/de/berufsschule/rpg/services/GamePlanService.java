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

  private ParserRunner parserRunner;

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

  // TODO: Load the GamePlan out of the Repo
  public GamePlan getGamePlan(String gamePlanName) {
    return parserRunner.getGames().get(gamePlanName);
  }
}
