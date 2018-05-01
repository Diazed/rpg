package de.berufsschule.rpg.services;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Game;
import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.User;
import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JumpService {

  private List<PageEvent> pageEvents;
  private GameService gameService;
  private PlayerService playerService;
  private DecisionService decisionService;
  private DeathService deathService;

  @Autowired
  public JumpService(List<PageEvent> pageEvents, PlayerService playerService,
      DecisionService decisionService, GameService gameService, DeathService deathService) {
    this.pageEvents = pageEvents;
    this.playerService = playerService;
    this.decisionService = decisionService;
    this.gameService = gameService;
    this.deathService = deathService;
  }

  private void runPageEvents(Page page, Player player) {
    for (PageEvent pageEvent : pageEvents) {
      pageEvent.event(page, player);
    }
    playerService.savePlayer(player);
  }

  public Page playPage(User user, Integer gamePlanId) {
    Game game = gameService.loadOrCreateGame(gamePlanId, user);
    if (game == null) {
      return null;
    }
    gameService.switchCurrentGame(game.getId(), gamePlanId, user);
    GamePlan gamePlan = game.getGamePlan();
    Player player = game.getPlayer();
    playerService.firstStart(player, gamePlan.getStartPage());
    deathService.playerDeath(player, gamePlan.getDeathPage());
    gameService.saveGame(game);
    Page page = findPageInGamePlan(gamePlan, player.getPosition());
    if (page == null) { // TODO: WTF?!
      page = new Page();
      page.setName("Fortsetzung folgt");
      page.setStorytext("Der von dir eingeschlagene Weg endet (vorerst) hier. Der Autor hat das "
          + "Ergebnis deiner letzten Entscheidung noch nicht verfasst. \n\n:c");
    }
    setFlagsForPagePossibilities(page, player);
    return page;
  }

  public boolean jumpToNextPage(User user, Integer gamePlanId, Integer clickedPossibilityId) {

    Game game = gameService.loadOrCreateGame(gamePlanId, user);
    Decision decision = decisionService
        .getDecision(clickedPossibilityId);
    return decision != null && handleDecision(decision, game, user);
  }

  private boolean handleDecision(Decision decision, Game game, User user) {
    Page jumpPage = findPageInGamePlan(game.getGamePlan(), decision.getMainJump());
    Player player = game.getPlayer();
    if (jumpPage == null) {
      jumpPage = new Page();
    } else {
      runPageEvents(jumpPage, player);
    }
    playerService.runAllPlayerEvents(player);

    if (isPlayerAllowedToJump(decision, game)) {
      player.setPosition(null);
      decisionService.runPossibilityEvents(decision, player, jumpPage);
      if (player.getPosition() == null) {
        player.setPosition(decision.getMainJump());
      }
      playerService.savePlayer(player);
      return true;
    } else {
      return false;
    }
  }

  private boolean isPlayerAllowedToJump(Decision decision, Game game) {
    if (deathService.revive(game.getPlayer(), game.getGamePlan().getStartPage())) {
      return true;
    }
    return playerService.doesPlayerMeetRequirements(decision, game.getPlayer());
  }

  private Page findPageInGamePlan(GamePlan gamePlan, Integer pageId) {
    for (Page page : gamePlan.getPages()) {
      if (page.getId().equals(pageId)) {
        return page;
      }
    }
    return null;
  }

  private void setFlagsForPagePossibilities(Page page, Player player) {
    for (Decision decision : page.getDecisions()) {
      decisionService.setFlags(decision, player);
    }
  }
}
