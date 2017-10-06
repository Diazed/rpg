package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService {

  private List<PageEvent> pageEvents;
  private GameService gameService;
  private PlayerService playerService;
  private DecisionService decisionService;
  private DeathService deathService;

  @Autowired
  public PageService(List<PageEvent> pageEvents, PlayerService playerService, DecisionService decisionService, GameService gameService, DeathService deathService) {
    this.pageEvents = pageEvents;
    this.playerService = playerService;
    this.decisionService = decisionService;
    this.gameService = gameService;
    this.deathService = deathService;
  }

  public void runPageEvents(Page page, Player player) {
    for (PageEvent pageEvent : pageEvents) {
      pageEvent.event(page, player);
    }
    playerService.savePlayer(player);
  }

  public Page playPage(User user, String gamename) {
    gameService.switchCurrentGame(gamename, user);
    Game game = gameService.loadOrCreateGame(gamename, user);
    if (game == null) {
      return null;
    }
    Player player = game.getPlayer();
    playerService.firstStart(player, game.getStartPage());
    deathService.playerDeath(player, game.getDeathPage());
    Page page = game.getPages().get(player.getPosition());
    if (page == null) {
      page = new Page();
      page.setName("Fortsetzung folgt");
      page.setStorytext("Der von dir eingeschlagene Weg endet (vorerst) hier. Der Autor hat das "
          + "Ergebnis deiner letzten Entscheidung noch nicht verfasst. \n\n:c");
    }
    setFlagsForPageDecisions(page, player);
    gameService.editGame(game, user.getId());
    return page;
  }

  public boolean jumpToNextPage(User user, String gamename, String jump) {
    Game game = gameService.loadOrCreateGame(gamename, user);
    Player player = game.getPlayer();
    Page originPage = game.getPages().get(player.getPosition());
    Decision clickedDecision = decisionService.getClickedDecision(originPage, jump);
    Page jumpPage = game.getPages().get(jump);
    if (jumpPage == null) {
      jumpPage = new Page();
    }
    if (clickedDecision == null)
      return false;
    runPageEvents(jumpPage, player);
    playerService.runAllPlayerEvents(player);
    if (!player.getAlive()) {
      return true;
    }
    if (deathService.revive(player, game.getStartPage())) {
      return true;
    }
    if (!playerService.doesPlayerMeetRequirements(clickedDecision, player)) {
      return false;
    }
    player.setPosition(null);
    decisionService.runDecisionEvents(clickedDecision, player, jumpPage);
    if (player.getPosition() == null) {
      player.setPosition(jump);
    }
    gameService.editGame(game, user.getId());
    return true;
  }

  private void setFlagsForPageDecisions(Page page, Player player) {
    for (Decision decision : page.getDecisions()) {
      decisionService.setFlags(decision, player);
    }
  }
}
