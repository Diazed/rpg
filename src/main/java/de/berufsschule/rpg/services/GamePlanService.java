package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.repositories.GamePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GamePlanService {

  private GamePlanRepository gamePlanRepository;

  @Autowired
  public GamePlanService(GamePlanRepository gamePlanRepository) {
    this.gamePlanRepository = gamePlanRepository;
  }

  public List<GamePlan> getGamePlanList() {

    Iterable<GamePlan> all = gamePlanRepository.findAll();
    List<GamePlan> gamePlans = new ArrayList<GamePlan>();
    all.forEach(gamePlans::add);
    return gamePlans;
  }

  public void save(GamePlan gamePlan) {
    gamePlanRepository.save(gamePlan);
  }

  public GamePlan getGamePlan(Integer id) {
    return gamePlanRepository.findOne(id);
  }
}
