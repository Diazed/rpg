package de.berufsschule.rpg.services;

import de.berufsschule.rpg.dto.GameDTO;
import de.berufsschule.rpg.dto.converter.GameDTOConvertor;
import de.berufsschule.rpg.eventhandling.pageevents.PageEvent;
import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Possibility;
import de.berufsschule.rpg.model.Question;
import de.berufsschule.rpg.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JumpService {

  private List<PageEvent> pageEvents;
  private GameService gameService;
  private PlayerService playerService;
  private PossibilityService possibilityService;
  private DeathService deathService;

  @Autowired
  public JumpService(List<PageEvent> pageEvents, PlayerService playerService,
      PossibilityService possibilityService, GameService gameService, DeathService deathService) {
    this.pageEvents = pageEvents;
    this.playerService = playerService;
    this.possibilityService = possibilityService;
    this.gameService = gameService;
    this.deathService = deathService;
  }

  public void runPageEvents(Page page, Player player) {
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
    gameService.switchCurrentGame(game.getId(), user);
    GamePlan gamePlan = game.getGamePlan();
    Player player = game.getPlayer();
    playerService.firstStart(player, gamePlan.getStartPage());
    deathService.playerDeath(player, gamePlan.getDeathPage());
    gameService.saveGame(game);
    Page page = findPageInGamePlan(gamePlan, player.getPosition());
    if (page == null) {
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
    Possibility clickedPossibility = possibilityService
        .getPossibility(clickedPossibilityId);
    if (clickedPossibility == null) {
      return false;
    }

    if (clickedPossibility.getClass() == Decision.class) {
      Decision decision = (Decision) clickedPossibility;
      return handleDecision(decision, game, user);
    } else {
      Question question = (Question) clickedPossibility;
      return handleQuestion(question, game, user);
    }
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
    GamePlan gamePlan = game.getGamePlan();
    Page jumpPage = gamePlan.getPages().get(player.getPosition());
    question.setAsked(true);
    possibilityService.runPossibilityEvents(question, player, jumpPage);
    if (question.isTakeAlt()) {
      gamePlan.getPages().get(jumpPage.getId()).setStorytext(question.getAltAnswer());
    } else {
      gamePlan.getPages().get(jumpPage.getId()).setStorytext(question.getMainAnswer());
    }
    return isPlayerAllowedToJump(question, user, game);
  }

  private boolean isPlayerAllowedToJump(Possibility possibility, User user, Game game) {
    if (deathService.revive(game.getPlayer(), game.getGamePlan().getStartPage())) {
      return true;
    }
    return playerService.doesPlayerMeetRequirements(possibility, game.getPlayer());
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
    for (Possibility possibility : page.getPossibilities()) {
      possibilityService.setFlags(possibility, player);
    }
  }
}
