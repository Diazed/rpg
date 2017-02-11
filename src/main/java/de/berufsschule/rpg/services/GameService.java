package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.*;
import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private GameRepository gameRepository;
  private ParserRunner parserRunner;
  private UserService userService;
  private GamePlanService gamePlanService;

  @Autowired
  public GameService(ParserRunner parserRunner, GameRepository gameRepository, UserService userService, GamePlanService gamePlanService) {
    this.parserRunner = parserRunner;
    this.gameRepository = gameRepository;
    this.userService = userService;
    this.gamePlanService = gamePlanService;
  }

  public Game getGame(String gameName, Integer userId) {
    return gameRepository.findByNameAndUserId(gameName, userId);
  }

  public void editGame(Game game, Integer userID) {
    Game origGame = getGame(game.getName(), userID);
    origGame.setDeathPage(game.getDeathPage());
    origGame.setStartPage(game.getStartPage());
    origGame.setPlayer(game.getPlayer());
    origGame.setName(game.getName());
    origGame.setPages(game.getPages());
    origGame.setItems(game.getItems());

    gameRepository.save(origGame);
  }

  public void initiateGames() {
    parserRunner.parseAllGames();
  }

  public void switchCurrentGame(String gameName, User user) {
    if (user.getCurrentGame() == null) {
      user.setCurrentGame(gameName);
    }
    if (!user.getCurrentGame().equals(gameName)) {
      user.setCurrentGame(gameName);
      userService.editUser(user);
    }
  }

  public Game loadOrCreateGame(String gameName, User user) {

    for (String save : user.getSavedGames()) {
      if (save.equals(gameName)) {
        return loadSavedGame(gameName, user);
      }

    }
    return createNewGame(gameName, user);
  }

  private Game createNewGame(String gameName, User user) {
    Game game = new Game();
    GamePlan gamePlan = gamePlanService.getGamePlan(gameName);
    game.setName(gameName);
    game.setUserId(user.getId());
    game.setDeathPage(gamePlan.getDeathPage());
    game.setStartPage(gamePlan.getStartPage());

    Player player = new Player();
    game.setPlayer(player);
    fillPagesAndItems(gameName, game);
    user.getSavedGames().add(gameName);
    userService.editUser(user);
    gameRepository.save(game);
    return game;
  }

  private Game loadSavedGame(String gameName, User user) {
    Game savedGame = getGame(gameName, user.getId());
    fillPagesAndItems(gameName, savedGame);
    return savedGame;
  }

  private void fillPagesAndItems(String gameName, Game game) {
    if (game.getItems() == null || game.getItems().isEmpty())
      game.setItems(gamePlanService.getItemHashMapOfGamePlan(gameName));
    if (game.getPages() == null || game.getPages().isEmpty())
      game.setPages(gamePlanService.getPageHashMapOfGamePlan(gameName));
  }
}
