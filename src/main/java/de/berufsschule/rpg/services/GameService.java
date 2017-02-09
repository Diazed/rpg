package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.*;
import de.berufsschule.rpg.parser.ParserRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class GameService {

  private ParserRunner parserRunner;
  private ItemService itemService;
  private PlayerService playerService;
  private DecisionService decisionService;

  @Autowired
  public GameService(ParserRunner parserRunner, ItemService itemService, PlayerService playerService, DecisionService decisionService){
    this.parserRunner = parserRunner;
    this.itemService = itemService;
    this.playerService = playerService;
    this.decisionService = decisionService;
  }

  public Game getGame(String gameName){
    return parserRunner.getGames().get(gameName);
  }

  public void useItem(Integer id, Player player){
    Item usedItem = itemService.findItemById(id);
    removeItemFromPlayer(player, usedItem);
    itemService.itemEffects(usedItem, player);
    playerService.editPlayer(player, player.getId());
  }

  public Page preparePage(Player player, String gameName){
    Game game = parserRunner.getGames().get(gameName);
    if (game == null){
      return null;
    }

    playerService.firstStart(player, gameName, game.getStartPage());

    if (!player.getLiveStatusInGame().get(gameName)){
      playerService.playerDeath(player, game.getDeathPage(), gameName);
    }


    Page page = getPageFromPlayerPosition(player, game);
    decisionService.prepareDecisions(page, player);

    return page;
  }

  public void initiateGame(){

      parserRunner.parseAllGames();

  }

  public boolean prepareJump(Player player, String jump, String gameName){
    Game game = parserRunner.getGames().get(gameName);
    if (game == null)
      return false;

    String deathPage = game.getDeathPage();
    Page currentPage = getPageFromPlayerPosition(player, game);
    Decision clickedDecision = decisionService.getClickedDecision(currentPage, jump);
    playerService.roundEffects(player, gameName, deathPage, clickedDecision, 3, 15);
    if (!player.getLiveStatusInGame().get(gameName))
      return true;
    if (isPlayerOnDeathPage(player, gameName, deathPage, currentPage)) return true;

    Page jumpPage = getJumpPage(jump, game);
    if (isJumpPossible(clickedDecision, player) ) {
      if (decisionService.isFailPossible(clickedDecision)){
        decisionService.decisionFailOrSuccess(clickedDecision.getProbability(), clickedDecision.getAlternativeJump(), game.getName(), jump, player);
      }else {
        player.getPosition().put(game.getName(), jump);
      }
      checkpointHandling(player, jumpPage);
      if (!jumpPage.getItems().isEmpty()) {
        addPageItemsToPlayer(jumpPage, player);
      }
      if (doesDecisionNeedItem(jumpPage)) {
        removeDecisionItemFromPlayer(player, jumpPage);
      }
      playerService.editPlayer(player, player.getId());
    }
    return true;
  }

  private boolean isPlayerOnDeathPage(Player player, String gameName, String deathPage, Page currentPage) {
    if (currentPage.getName().equals("R.I.P.") || currentPage.getName().equals(deathPage)){
      if (player.getCheckpoint() == null || player.getCheckpoint().equals("")){
        player.getPosition().put(gameName, "start");
      } else {
        player.getPosition().put(gameName, player.getCheckpoint());
      }
      playerService.editPlayer(player, player.getId());
      return true;
    }
    return false;
  }

  public List<Game> getGameList(){
    Iterator<Game> itr = parserRunner.getGames().values().iterator();
    List<Game> games = new ArrayList<>();
    while (itr.hasNext()){
      games.add(itr.next());
    }
    return games;
  }



  private void checkpointHandling(Player player, Page page){
    if (page.isCheckpoint())
      player.setCheckpoint(page.getName());
  }



  private boolean doesDecisionNeedItem(Page jumpPage) {
    return !Objects.equals(jumpPage.getUsedItem(), "");
  }

  private void addPageItemsToPlayer(Page page, Player player) {
    for (int i = 0; i < page.getItems().size(); i++) {

      page.getItems().get(i).setPlayerId(player.getId());
      itemService.saveNewItem(page.getItems().get(i));
      player.getItems().add(itemService.findItemByNameAndPlayerId(page.getItems().get(i).getName(), player.getId()));
    }
  }

  private void removeDecisionItemFromPlayer(Player player, Page jumpPage) {
    for (int i = 0; i < player.getItems().size(); i++) {
      String usedItem = jumpPage.getUsedItem();
      if (player.getItems().get(i).getName().equals(usedItem)) {

        removeItemFromPlayer(player, itemService.findItemByNameAndPlayerId(usedItem, player.getId()));

        break;
      }
    }
  }

  private void removeItemFromPlayer(Player player, Item item) {
    Integer playerItemSize = player.getItems().size();
    for (int i = 0; i < playerItemSize; i++) {
      if (Objects.equals(player.getItems().get(i).getId(), item.getId())) {
        Item playerItem = player.getItems().get(i);
        if (playerItem.getAmount() - 1 != 0) {
          item.setAmount(playerItem.getAmount() - 1);
          itemService.editItemAmount(item);
          break;
        } else {
          player.getItems().remove(item);
          break;
        }
      }
    }
  }

  private Page getPageFromPlayerPosition(Player player, Game game) {
    Page page = new Page();
    int pagesSize = game.getPages().size();
    for (int i = 0; i < pagesSize; i++) {
      if (Objects.equals(game.getPages().get(i).getName(), player.getPosition().get(game.getName()))) {
        page = game.getPages().get(i);
        break;
      }
    }
    return page;
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
    Integer playerItemSize = player.getItems().size();
    for (int i = 0; i < playerItemSize; i++) {
      if (Objects.equals(player.getItems().get(i).getName(), decision.getNeededItem())) {
        return true;
      }
    }
    return false;
  }

  private Page getJumpPage(String jump, Game game) {
    for (int i = 0; i < game.getPages().size(); i++) {
      if (game.getPages().get(i).getName().equals(jump)) {
        return game.getPages().get(i);
      }
    }
    return new Page();
  }
}
