package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import de.berufsschule.rpg.item.ItemService;
import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.player.Player;
import de.berufsschule.rpg.player.PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public Page preparePage(Player player){
    Page page = getCurrentPageFromPlayer(player);

    page = prepareDecisions(page, player);
    if (Objects.equals(page.getName(), "R.I.P.")){
      player.setItems(new ArrayList<>());
      playerService.editPlayer(player, player.getId());
    }
    return page;
  }

  public void prepareJump(Player player, String jump){
    Page currentPage = getCurrentPageFromPlayer(player);
    Decision clickedDecision = getClickedDecision(currentPage, jump);

    if (playerDied(currentPage)){
      jump = player.getCheckpoint();
    }
    Page jumpPage = getJumpPage(jump);
    if (isJumpPossible(clickedDecision, jumpPage, player) || playerDied(currentPage)) {
      if (isFailPossible(clickedDecision)){
        decisionFailOrSuccess(clickedDecision.getProbability(), jump, clickedDecision.getAlternativeJump(), player);
      }else {
        player.setLevel(jump);
      }

      checkpointHandling(player, jumpPage);
      if (playerDied(currentPage)){
        respawn(player);
      }else {
        roundEffects(player);
      }
      if (!jumpPage.getItems().isEmpty()) {
        addPageItemsToPlayer(jumpPage, player);
      }
      if (doesDecisionNeedItem(jumpPage) && !playerDied(currentPage)) {
        removeDecisionItemFromPlayer(player, jumpPage);
      }
      playerService.editPlayer(player, player.getId());
    }
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

  private void decisionFailOrSuccess(int probability, String jump, String failJump, Player player){

    if (probability >= 100)
      player.setLevel(jump);
    int random = ThreadLocalRandom.current().nextInt(1, 100 + 1);
    if (random > probability)
      player.setLevel(failJump);


  }

  private boolean playerDied(Page page){
    return Objects.equals(page.getName(), "R.I.P.");
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

  private void roundEffects(Player player) {
    roundHunger(player);
    roundThirst(player);
  }

  private void roundThirst(Player player) {
    if (player.getThirst() + 5 > 100) {
      player.setLevel("R.I.P.");
    } else {
      player.setThirst(player.getThirst() + 5);
    }
  }

  private void roundHunger(Player player) {
    if (player.getHunger() + 3 > 100) {
      player.setLevel("R.I.P.");
    } else {
      player.setHunger(player.getHunger() + 3);
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

  private Page getCurrentPageFromPlayer(Player player) {
    Game game = parserRunner.getGame();
    Page page = new Page();
    int pagesSize = game.getPages().size();
    for (int i = 0; i < pagesSize; i++) {
      if (Objects.equals(game.getPages().get(i).getName(), player.getLevel())) {
        page = game.getPages().get(i);
        break;
      }
    }
    return page;
  }

  private boolean isJumpPossible(Decision clickedDecision, Page jumpPage, Player player) {

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

  private Page getJumpPage(String jump) {
    Game game = parserRunner.getGame();
    for (int i = 0; i < game.getPages().size(); i++) {
      if (game.getPages().get(i).getName().equals(jump)) {
        return game.getPages().get(i);
      }
    }
    return new Page();
  }

}
