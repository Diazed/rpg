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

  public void saveGame(Game game) {
    gameRepository.save(game);
  }

  public void initiateGames() {
    parserRunner.parseAllGames();
  }

  public void switchCurrentGame(Integer gameId, User user) {
    if (user.getCurrentGame() == null) {
      log.info(user.getUsername() + " started his very first game.");
      user.setCurrentGame(gameId);
    }
    if (!user.getCurrentGame().equals(gameId)) {
      user.setCurrentGame(gameId);
    }
    userService.editUser(user);
  }

  public Game loadGame(Integer gamePlanId, User user){
    for (Game game : user.getSavedGames()) {
      if (game.getGamePlan().getId().equals(gamePlanId)) {
        return getGame(game.getId());
      }
    }
    return null;
  }

  public Game loadOrCreateGame(Integer gamePlanId, User user) {

    Game loadedGame = loadGame(gamePlanId, user);

    if (loadedGame != null){
      return loadedGame;
    }

    log.info("Create Game with GamePlanId " + gamePlanId + " for user " + user.getUsername() + " (" + user
        .getEmail()
        + ").");
    return createNewGame(gamePlanId, user);
  }

  private Game createNewGame(Integer gamePlanId, User user) {
    GamePlan gamePlan = gamePlanService.getGamePlan(gamePlanId);
    if (gamePlan == null) {
      log.info("User (" + user.getUsername() + ") request a non existent game!");
      return null;
    }

    Game game = new Game();
    game.setUserId(user.getId());
    game.setGamePlan(gamePlan);

    Player player = new Player();
    game.setPlayer(player);
    user.getSavedGames().add(game);
    try {
      gameRepository.save(game);
      log.info("Saved game " + game.getGamePlan().getName() + ". GameId is " + game.getId());
      userService.editUser(user);
    } catch (Exception e) {
      log.error("Created game could not be saved! Error: " + e.getMessage());
    }
    return game;
  }
}
