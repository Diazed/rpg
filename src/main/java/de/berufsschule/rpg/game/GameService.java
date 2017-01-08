package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import de.berufsschule.rpg.item.ItemService;
import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.player.Player;
import de.berufsschule.rpg.player.PlayerService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

  ParserRunner parserRunner;
  ItemService itemService;
  PlayerService playerService;

  public GameService(ParserRunner parserRunner, ItemService itemService, PlayerService playerService){
    this.parserRunner = parserRunner;
    this.itemService = itemService;
    this.playerService = playerService;
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
    if (firstStart(player, game.getName())){
      player.getPosition().put(game.getName(), game.getStartPage());
      playerService.editPlayer(player, player.getId());
    }

    Page page = getCurrentPageFromPlayer(player, game);

    prepareDecisions(page, player);
    if (playerDied(page, game.getDeathPage())){
      player.setItems(new ArrayList<>());
      playerService.editPlayer(player, player.getId());
    }
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

    String deathPage = game.getDeathPage();
    Page currentPage = getCurrentPageFromPlayer(player, game);
    Decision clickedDecision = getClickedDecision(currentPage, jump);

    if (playerDied(currentPage, deathPage)){
      jump = player.getCheckpoint();
    }
    Page jumpPage = getJumpPage(jump, game);
    if (isJumpPossible(clickedDecision, player) || playerDied(currentPage, deathPage)) {
      if (isFailPossible(clickedDecision)){
        decisionFailOrSuccess(clickedDecision.getProbability(), clickedDecision.getAlternativeJump(), game.getName(), jump, player);
      }else {
        player.getPosition().put(game.getName(), jump);
      }

      checkpointHandling(player, jumpPage);
      if (playerDied(currentPage, deathPage)){
        respawn(player);
      }else {
        roundHunger(player, gameName, deathPage);
        roundThirst(player, gameName, deathPage);
        roundInjury(player, gameName, deathPage, clickedDecision);
        roundExp(player);
      }
      if (!jumpPage.getItems().isEmpty()) {
        addPageItemsToPlayer(jumpPage, player);
      }
      if (doesDecisionNeedItem(jumpPage) && !playerDied(currentPage, deathPage)) {
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

  private boolean firstStart(Player player, String gameName) {
    if (Objects.equals(player.getPosition().get(gameName), null))
      return true;
    return false;
  }


  private void respawn(Player player){
    player.setHunger(0);
    player.setThirst(0);
    player.setHitpoints(100);
  }

  private boolean isFailPossible(Decision clickedDecision){
    if (clickedDecision.getProbability() != 0 && clickedDecision.getAlternativeJump() != null){
      return true;
    }
    return false;
  }

  private void decisionFailOrSuccess(int probability, String failJump,String gameName, String jump, Player player){

    if (probability >= 100)
      player.getPosition().put(gameName, jump);
    int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
    if (random > probability){
      player.getPosition().put(gameName, failJump);
    }else {
      player.getPosition().put(gameName, jump);
    }



  }

  private boolean playerDied(Page page, String deathPage){
    return Objects.equals(page.getName(), "R.I.P.") || Objects.equals(page.getName(), deathPage);
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
  
  private void roundExp(Player player){
    Integer playerLvl = player.getPlayerLvl();
    Integer playerXp = player.getExp();
    Integer neededXp = 0;

    for (int i=0; i<playerLvl; i++){
      neededXp += i * 50;
    }
    neededXp = neededXp - playerXp;

    if (neededXp - 10 < 0){
      player.setPlayerLvl(playerLvl + 1);
      player.setExp(0);
    } else {
      player.setExp(playerXp + 10);
    }


  }

  private void roundInjury(Player player, String gameName, String deathPage, Decision clickedDecision){

    int injury = clickedDecision.getInjury();
    int playerHealth = player.getHitpoints();

    if (injury > 0){
      if (playerHealth - injury < 0){
        playerDied(player, gameName, deathPage);
      } else {
        player.setHitpoints(playerHealth - injury);
      }
    }
  }

  private void roundThirst(Player player, String gameName, String deathPage) {
    if (player.getThirst() + 5 > 100) {
      playerDied(player, gameName, deathPage);
    } else {
      player.setThirst(player.getThirst() + 5);
    }
  }

  private void roundHunger(Player player, String gameName, String deathPage) {
    if (player.getHunger() + 3 > 100) {
      playerDied(player, gameName, deathPage);
    } else {
      player.setHunger(player.getHunger() + 3);
    }
  }

  private void playerDied(Player player, String gameName, String deathPage){
    if (deathPage == null || Objects.equals(deathPage, "")) {
      player.getPosition().put(gameName, "R.I.P.");
    }else {
      player.getPosition().put(gameName, deathPage);
    }
  }

  private Page prepareDecisions(Page page, Player player) {
    Integer pageDecisions = page.getDecisions().size();
    Integer playerItems = player.getItems().size();
    for (int i = 0; i < pageDecisions; i++) {
      if (!Objects.equals(page.getDecisions().get(i).getNeededItem(), null)) {
        for (int j = 0; j < playerItems; j++) {
          if (Objects.equals(page.getDecisions().get(i).getNeededItem(), player.getItems().get(j).getName())) {
            page.getDecisions().get(i).setHasItem(true);
          }
        }
      }
    }
    return page;
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

  private Page getCurrentPageFromPlayer(Player player, Game game) {
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

  private Decision getClickedDecision(Page currentPlayerPage, String jump) {
    Decision jumpDecision = new Decision();
    for (int i = 0; i < currentPlayerPage.getDecisions().size(); i++) {
      if (isJumpInPlayerPage(currentPlayerPage.getDecisions().get(i), jump))
        jumpDecision = currentPlayerPage.getDecisions().get(i);
    }
    return jumpDecision;
  }

  private boolean isJumpInPlayerPage(Decision decision, String jump) {
    return Objects.equals(decision.getJump(), jump);
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
