package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Question;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.repositories.PageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService {

  private List<PageEvent> pageEvents;
  private GameService gameService;
  private PlayerService playerService;
  private PossibilityService possibilityService;
  private PageRepository pageRepository;
  private DeathService deathService;

  @Autowired
  public PageService(List<PageEvent> pageEvents, PlayerService playerService,
      PossibilityService possibilityService, GameService gameService,
      PageRepository pageRepository, DeathService deathService) {
    this.pageEvents = pageEvents;
    this.playerService = playerService;
    this.possibilityService = possibilityService;
    this.gameService = gameService;
    this.pageRepository = pageRepository;
    this.deathService = deathService;
  }

  public void savePage(Page page) {
    pageRepository.save(page);
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
    playerService.firstStart(player, game.getGamePlan().getStartPage());
    deathService.playerDeath(player, game.getGamePlan().getDeathPage());
    Page page = game.getGamePlan().getPages().get(player.getPosition());
    if (page == null) {
      page = new Page();
      page.setName("Fortsetzung folgt");
      page.setStorytext("Der von dir eingeschlagene Weg endet (vorerst) hier. Der Autor hat das "
          + "Ergebnis deiner letzten Entscheidung noch nicht verfasst. \n\n:c");
    }
    setFlagsForPagePossibilities(page, player);
    gameService.editGame(game);
    return page;
  }

  public boolean jumpToNextPage(User user, String gamename, Integer clickedPossibilityId) {

    Game game = gameService.loadOrCreateGame(gamename, user);
    Player player = game.getPlayer();
    Possibility clickedPossibility = possibilityService
        .getPossibility(clickedPossibilityId);
    if (clickedPossibility == null)
      return false;

    if (clickedPossibility.getClass() == Decision.class) {
      Decision decision = (Decision) clickedPossibility;
      return handleDecision(decision, game, user);
    } else {
      Question question = (Question) clickedPossibility;
      return handleQuestion(question, game, user);
    }
  }

  private boolean handleDecision(Decision decision, Game game, User user) {
    Page jumpPage = game.getGamePlan().getPages().get(decision.getMainJump());
    Player player = game.getPlayer();
    if (jumpPage == null) {
      jumpPage = new Page();
    } else {
      runPageEvents(jumpPage, player);
    }
    playerService.runAllPlayerEvents(player);

    if (isPlayerAllowedToJump(decision, user, game)) {
      player.setPosition(null);
      possibilityService.runPossibilityEvents(decision, player, jumpPage);
      if (player.getPosition() == null) {
        player.setPosition(decision.getMainJump());
      }
      playerService.savePlayer(player);
      return true;
    } else {
      return false;
    }
  }

  private boolean handleQuestion(Question question, Game game, User user) {

    Player player = game.getPlayer();
    Page jumpPage = game.getGamePlan().getPages().get(player.getPosition());
    question.setAsked(true);
    possibilityService.runPossibilityEvents(question, player, jumpPage);
    if (question.isTakeAlt()) {
      game.getGamePlan().getPages().get(jumpPage.getId()).setStorytext(question.getAltAnswer());
    } else {
      game.getGamePlan().getPages().get(jumpPage.getId()).setStorytext(question.getMainAnswer());
    }
    return isPlayerAllowedToJump(question, user, game);
  }

  private boolean isPlayerAllowedToJump(Possibility possibility, User user, Game game) {
    if (deathService.revive(game.getPlayer(), game.getGamePlan().getStartPage())) {
      return true;
    }
    if (playerService.doesPlayerMeetRequirements(possibility, game.getPlayer())) {
      gameService.editGame(game);
      return true;
    } else {
      return false;
    }
  }

  private void setFlagsForPagePossibilities(Page page, Player player) {
    for (Possibility possibility : page.getPossibilities()) {
      possibilityService.setFlags(possibility, player);
    }
  }
}
