package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.pageeventhandling.PageEventHandler;
import de.berufsschule.rpg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {

  private List<PageEventHandler> pageEventHandlers;
  private GameService gameService;
  private PlayerService playerService;
  private DecisionService decisionService;

  @Autowired
  public PageService(List<PageEventHandler> pageEventHandlers, PlayerService playerService, DecisionService decisionService, GameService gameService) {
    this.pageEventHandlers = pageEventHandlers;
    this.playerService = playerService;
    this.decisionService = decisionService;
    this.gameService = gameService;
  }

  public void runPageEvents(Page page, Player player) {
    for (PageEventHandler pageEventHandler : pageEventHandlers) {
      pageEventHandler.event(page, player);
    }
  }

  public Page playPage(User user, String gamename) {
    gameService.switchCurrentGame(gamename, user);
    Game game = gameService.loadOrCreateGame(gamename, user);
    Player player = game.getPlayer();

    playerService.firstStart(player, game.getStartPage());
    if (!player.getAlive()) {
      playerService.playerDeath(player, game.getDeathPage());
    }

    Page page = game.getPages().get(player.getPosition());
    setHasItemFlagInPageDecisions(page, player);
    gameService.editGame(game, user.getId());
    return page;
  }

  public boolean jumpToNextPage(User user, String gamename, String jump) {
    Game game = gameService.loadOrCreateGame(gamename, user);
    Player player = game.getPlayer();
    Page originPage = game.getPages().get(player.getPosition());
    Page jumpPage = game.getPages().get(jump);

    Decision clickedDecision = decisionService.getClickedDecision(originPage, jump);
    if (clickedDecision == null)
      return false;
    playerService.roundEffects(player, 3, 5);

    if (!player.getAlive())
      return true;
    if (playerService.revive(player))
      return true;
    if (!playerService.doesPlayerMeetRequirements(clickedDecision, player))
      return false;

    runPageEvents(jumpPage, player);
    decisionService.runDecisionEvents(clickedDecision, player, jump, jumpPage);

    gameService.editGame(game, user.getId());
    return true;
  }

  private Page setHasItemFlagInPageDecisions(Page page, Player player) {

    for (Decision decision : page.getDecisions()) {
      String neededItem = decision.getUsedItem();
      if (neededItem != null) {
        for (String item : player.getItems()) {
          if (item.equals(neededItem)) {
            decision.setHasItem(true);
            break;
          }
        }
      }
    }

    return page;
  }

}
