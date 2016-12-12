package de.berufsschule.rpg.game;

import de.berufsschule.rpg.player.Player;
import de.berufsschule.rpg.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Objects;

@Controller
public class GameController {

  PlayerService playerService;
  Parser parser;

  @Autowired
  public GameController(PlayerService playerService, Parser parser) {
    this.playerService = playerService;
    this.parser = parser;
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String spielAufrufen(Model model, Principal principal) {

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());
    Page page = getCurentPageFromPlayer(loggedInPlayer);

    if (!page.getItems().isEmpty()) {
      addPageItemsToPlayer(page, loggedInPlayer);
    }
    model.addAttribute("page", page);
    return "game/ingame";
  }

  @RequestMapping(value = "/play/{jump}", method = RequestMethod.POST)
  public String playerLevelAnpassen(@PathVariable String jump, Principal principal) {

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());

    Page page = getCurentPageFromPlayer(loggedInPlayer);

    if (isJumpPossible(page, jump)) {
      loggedInPlayer.setLevel(jump);
      if (doesJumpPageUseItem(jump)) {
        removeUsedItemFromPlayer(loggedInPlayer, jump);
      }

    }

    return "redirect:/play";
  }

  private boolean doesJumpPageUseItem(String jump) {

    if (getJumpPage(jump).getUsedItem() != "")
      return true;
    return false;
  }

  private void addPageItemsToPlayer(Page page, Player player) {
    for (int j = 0; j < page.getItems().size(); j++) {
      player.getItems().add(page.getItems().get(j));
      playerService.editPlayer(player, player.getId());
    }
  }

  private void removeUsedItemFromPlayer(Player player, String jump) {
    for (int i = 0; i < player.getItems().size(); i++) {
      if (player.getItems().get(i).getName().equals(getJumpPage(jump).getUsedItem())) {
        player.getItems().remove(i);
        playerService.editPlayer(player, player.getId());
        break;
      }
    }
  }

  public Page getCurentPageFromPlayer(Player player) {
    Game game = parser.parser();
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

  public boolean isJumpPossible(Page page, String jump) {
    for (int i = 0; i < page.getDecisions().size(); i++) {
      if (Objects.equals(page.getDecisions().get(i).getJump(), jump))
        return true;
    }
    return false;
  }

  public Page getJumpPage(String jump) {
    Game game = parser.parser();

    for (int i = 0; i < game.getPages().size(); i++) {
      if (game.getPages().get(i).getName().equals(jump)) {
        return game.getPages().get(i);
      }
    }

    return new Page();
  }

}
