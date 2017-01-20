package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.*;
import de.berufsschule.rpg.parser.ParserRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class GameService {

  ParserRunner parserRunner;
  ItemService itemService;
  PlayerService playerService;
  DecisionService decisionService;

  @Autowired
  public GameService(ParserRunner parserRunner, ItemService itemService, PlayerService playerService){
    this.parserRunner = parserRunner;
    this.itemService = itemService;
    this.playerService = playerService;
    this.decisionService = new DecisionService();
  }

  public Game getGame(String gameName){
    return parserRunner.getGames().get(gameName);
  }

  public void prepareProfile(Integer id, Player player){
    Item usedItem = itemService.findItemById(id);
    removeItemFromPlayer(player, usedItem);
    itemEffects(usedItem, player);
    playerService.editPlayer(player, player.getId());
  }

  public Page preparePage(Player player, String gameName){
    Game game = parserRunner.getGames().get(gameName);
    if (game == null){
      return null;
    }

    playerService.firstStart(player, gameName, game.getStartPage());
    Page page = getPageFromPlayerPosition(player, game);
    decisionService.prepareDecisions(page, player);
    playerService.playerDeath(player, page, game.getDeathPage(), gameName);
    return page;
  }

  public void initiateGame(){
    List<String> fileNames = getFileNames();
    for (String fileName : fileNames) {
      parserRunner.parse(fileName);
    }
  }

  public boolean prepareJump(Player player, String jump, String gameName){
    Game game = parserRunner.getGames().get(gameName);
    if (game == null)
      return false;
    boolean dead = false;
    String deathPage = game.getDeathPage();
    Page currentPage = getPageFromPlayerPosition(player, game);
    Decision clickedDecision = decisionService.getClickedDecision(currentPage, jump);
    playerService.roundEffects(player, gameName, deathPage, clickedDecision, 3, 15);
    String playerPosition = player.getPosition().get(gameName);

    if (currentPage.getName().equals("R.I.P.") || currentPage.getName().equals(deathPage)){
      if (deathPage == null){
        jump = "start";
      } else {
        jump = player.getCheckpoint();
      }
      dead = true;
    }
    if (playerPosition.equals(deathPage) || playerPosition.equals("R.I.P.") && !dead)
      return true;

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

  public List<Game> getGameList(){
    Iterator<Game> itr = parserRunner.getGames().values().iterator();
    List<Game> games = new ArrayList<>();
    while (itr.hasNext()){
      games.add(itr.next());
    }
    return games;
  }

  private List<String> getFileNames(){
    List<String> fileNames = new ArrayList<>();

    ClassLoader classLoader = getClass().getClassLoader();
    File folder;
    folder = new File(classLoader.getResource("file").getFile());
    File[] listOfFiles = folder.listFiles();

    assert listOfFiles != null;
    for (File listOfFile : listOfFiles) {
      if (listOfFile.isFile()) {
        fileNames.add(listOfFile.getName());
      }
    }
    return fileNames;
  }

  private void checkpointHandling(Player player, Page page){
    if (page.isCheckpoint())
      player.setCheckpoint(page.getName());
  }

  private void itemEffects(Item item, Player player) {
    if (item.isDrink()) {
      itemDrink(item, player);
    } else {
      itemFood(item, player);
    }
    if (item.getHealing() >= 0)
      itemHealing(item, player);

  }

  private void itemHealing(Item item, Player player){
    Integer playerHealth = player.getHitpoints();
    Integer itemHealing = item.getHealing();
    if (playerHealth + itemHealing > 100){
      player.setHitpoints(100);
    } else {
      player.setHitpoints(playerHealth + itemHealing);
    }
  }

  private void itemFood(Item item, Player player) {
    Integer playerHunger = player.getHunger();
    Integer itemValue = item.getValue();
    if (playerHunger - itemValue < 0) {
      player.setHunger(0);
    } else {
      player.setHunger(playerHunger - itemValue);
    }
  }

  private void itemDrink(Item item, Player player) {
    Integer playerThirst = player.getThirst();
    Integer itemValue = item.getValue();
    if (playerThirst - itemValue < 0) {
      player.setThirst(0);
    } else {
      player.setThirst(playerThirst - itemValue);
    }
  }

  private boolean doesDecisionNeedItem(Page jumpPage) {
    return !Objects.equals(jumpPage.getUsedItem(), "");
  }

  private void addPageItemsToPlayer(Page page, Player player) {
    for (int j = 0; j < page.getItems().size(); j++) {

      page.getItems().get(j).setPlayerId(player.getId());
      itemService.saveNewItem(page.getItems().get(j));
      player.getItems().add(itemService.findItemByNameAndPlayerId(page.getItems().get(j).getName(), player.getId()));
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
          itemService.editItem(item);
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
