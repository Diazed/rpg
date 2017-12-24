package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.repositories.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameService {

  private GameRepository gameRepository;
  private ParserRunner parserRunner;
  private UserService userService;
  private GamePlanService gamePlanService;

  @Autowired
  public GameService(GameRepository gameRepository,
      ParserRunner parserRunner, UserService userService,
      GamePlanService gamePlanService) {
    this.gameRepository = gameRepository;
    this.parserRunner = parserRunner;
    this.userService = userService;
    this.gamePlanService = gamePlanService;
  }

  public Game getGame(Integer id) {
    return gameRepository.findOne(id);
  }

  public Game getGame(String gamename, User user) {
    for (Game game : user.getSavedGames()) {
      if (game.getGamePlan().getName().equals(gamename)) {
        return game;
      }
    }
    return null;
  }

  public void editGame(Game game) {
    gameRepository.save(game);
  }

  public void initiateGames() {
    parserRunner.parseAllGames();
  }

  public void switchCurrentGame(String gameName, User user) {
    if (user.getCurrentGame() == null) {
      log.info(user.getUsername() + " started his very first game.");
      user.setCurrentGame(gameName);
    }
    if (!user.getCurrentGame().equals(gameName)) {
      user.setCurrentGame(gameName);
      userService.editUser(user);
    }
  }

  public Game loadOrCreateGame(String gameName, User user) {

    for (Game game : user.getSavedGames()) {
      if (game.getGamePlan().getName().equals(gameName)) {
        return getGame(game.getId());
      }
    }
    log.info("Create game " + gameName + " for user " + user.getUsername() + " (" + user.getEmail()
        + ").");
    return createNewGame(gameName, user);
  }

  private Game createNewGame(String gameName, User user) {
    Game game = new Game();
    GamePlan gamePlan = gamePlanService.getGamePlan(gameName);
    if (gamePlan == null) {
      log.info("User (" + user.getUsername() + ") request a non existent game!");
      return null;
    }

    game.setUserId(user.getId());
    game.setGamePlan(gamePlan);

    Player player = new Player();
    game.setPlayer(player);
    user.getSavedGames().add(game);
    try {
      gameRepository.save(game);
      userService.editUser(user);
    } catch (Exception e) {
      log.error("Created game could not be saved! Error: " + e.getMessage());
    }
    return game;
  }
}
