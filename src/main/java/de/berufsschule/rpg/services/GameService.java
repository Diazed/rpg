package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.*;
import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameService {

  private GameRepository gameRepository;
  private ParserRunner parserRunner;
  private ItemService itemService;
  private PlayerService playerService;
  private DecisionService decisionService;
  private UserService userService;
  private GamePlanService gamePlanService;

  @Autowired
  public GameService(ParserRunner parserRunner, ItemService itemService, PlayerService playerService, DecisionService decisionService, GameRepository gameRepository, UserService userService, GamePlanService gamePlanService) {
    this.parserRunner = parserRunner;
    this.itemService = itemService;
    this.playerService = playerService;
    this.decisionService = decisionService;
    this.gameRepository = gameRepository;
    this.userService = userService;
    this.gamePlanService = gamePlanService;
  }

  public Game getGame(String gameName) {
    return gameRepository.findByName(gameName);
  }

  public Page getPage(String pageName, Game game) {
    return game.getPages().get(pageName);
  }


  public Page preparePage(User user, String gameName) {
    Game game = prepareGame(gameName, user);
    if (game == null) {
      return null;
    }
    Player player = game.getPlayer();
    playerService.firstStart(player, game.getStartPage());

    if (!player.getAlive()) {
      playerService.playerDeath(player, game.getDeathPage());
    }


    Page page = getPage(player.getPosition(), game);
    decisionService.prepareDecisions(page, player);

    return page;
  }

  public void initiateGames() {

      parserRunner.parseAllGames();

  }

  public boolean prepareJump(User user, String jump, String gameName) {
    switchGame(gameName, user);
    Game game = prepareGame(gameName, user);
    Player player = game.getPlayer();

    String deathPage = game.getDeathPage();
    Page currentPage = getPage(player.getPosition(), game);
    Decision clickedDecision = decisionService.getClickedDecision(currentPage, jump);
    playerService.roundEffects(player, clickedDecision, 3, 15);
    if (!player.getAlive())
      return true;
    if (isPlayerOnDeathPage(player, gameName, deathPage, currentPage)) return true;

    Page jumpPage = getPage(jump, game);
    if (isJumpPossible(clickedDecision, player) ) {
      if (decisionService.isFailPossible(clickedDecision)){
        decisionService.decisionFailOrSuccess(clickedDecision.getProbability(), clickedDecision.getAlternativeJump(), jump, player);
      }else {
        player.setPosition(jump);
      }
      checkpointHandling(player, jumpPage);
      if (!jumpPage.getItems().isEmpty()) {
        addPageItemsToPlayer(jumpPage, player);
      }
      if (doesDecisionNeedItem(clickedDecision)) {
        removeDecisionItemFromPlayer(player, clickedDecision);
      }
      playerService.editPlayer(player);
    }
    gameRepository.save(game);
    return true;
  }

  public void switchGame(String gameName, User user) {
    if (!user.getCurrentGame().equals(gameName)) {
      user.setCurrentGame(gameName);
      userService.editUser(user);
    }
  }

  private Game prepareGame(String gameName, User user) {


    for (Game save : user.getSavedGames()) {
      if (save.getName().equals(gameName)) {
        fillPagesAndItems(gameName, save);
        return save;
      }

    }

    Game game = getGame(gameName);
    fillPagesAndItems(gameName, game);

    return game;
  }

  private void fillPagesAndItems(String gameName, Game game) {
    if (game.getItems() == null || game.getItems().isEmpty())
      game.setItems(gamePlanService.getItemHashMapOfGamePlan(gameName));
    if (game.getPages() == null || game.getPages().isEmpty())
      game.setPages(gamePlanService.getPageHashMapOfGamePlan(gameName));
  }

  private boolean isPlayerOnDeathPage(Player player, String gameName, String deathPage, Page currentPage) {
    if (currentPage.getName().equals("R.I.P.") || currentPage.getName().equals(deathPage)){
      if (player.getCheckpoint() == null || player.getCheckpoint().equals("")){
        player.setPosition("start");
      } else {
        player.setPosition(player.getCheckpoint());
      }
      playerService.editPlayer(player);
      return true;
    }
    return false;
  }




  private void checkpointHandling(Player player, Page page){
    if (page.isCheckpoint())
      player.setCheckpoint(page.getName());
  }


  private boolean doesDecisionNeedItem(Decision clickedDecision) {
    return !Objects.equals(clickedDecision.getNeededItem(), "") && !Objects.equals(clickedDecision.getNeededItem(), null);
  }

  private void addPageItemsToPlayer(Page page, Player player) {

    for (String item : page.getItems()) {
      player.getItems().add(item);
    }
  }

  private void removeDecisionItemFromPlayer(Player player, Decision clickedDecision) {
    String usedItem = clickedDecision.getNeededItem();

    for (String item : player.getItems()) {
      if (item.equals(usedItem))
        itemService.useItem(usedItem, player);
    }

  }

  private boolean isJumpPossible(Decision clickedDecision, Player player) {

    if (clickedDecision.getJump() == null) {
      return false;
    } else {
      if (doesDecisionRequireItem(clickedDecision)) {
        return doesPlayerOwnRequiredItem(clickedDecision, player);
      } else {
        return true;
      }
    }
  }

  private boolean doesDecisionRequireItem(Decision decision) {
    return decision.getItem().getName() != null;
  }

  private boolean doesPlayerOwnRequiredItem(Decision decision, Player player) {
    String neededItem = decision.getNeededItem();
    for (String item : player.getItems()) {
      if (item.equals(neededItem))
        return true;
    }
    return false;
  }
}
