package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import de.berufsschule.rpg.item.ItemService;
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

  ItemService itemService;
  PlayerService playerService;
  Parser parser;

  @Autowired
  public GameController(ItemService itemService, PlayerService playerService, Parser parser) {
    this.itemService = itemService;
    this.playerService = playerService;
    this.parser = parser;
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String invokeGame(Model model, Principal principal) {

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());
    Page page = getCurrentPageFromPlayer(loggedInPlayer);
    model.addAttribute("page", page);
    return "game/ingame";
  }

  @RequestMapping(value = "/play/{jump}", method = RequestMethod.POST)
  public String goToNextPage(@PathVariable String jump, Principal principal) {

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());

    Page page = getCurrentPageFromPlayer(loggedInPlayer);

    if (isJumpPossible(page, jump)) {
      loggedInPlayer.setLevel(jump);
      if (!getJumpPage(jump).getItems().isEmpty()){
        addPageItemsToPlayer(getJumpPage(jump), loggedInPlayer);
      }
      if (doesJumpPageUseItem(jump)) {
        removeUsedItemFromPlayer(loggedInPlayer, jump);
      }
      playerService.editPlayer(loggedInPlayer, loggedInPlayer.getId());
    }

    return "redirect:/play";
  }

  private boolean doesJumpPageUseItem(String jump) {

    return !Objects.equals(getJumpPage(jump).getUsedItem(), "");
  }

  private void addPageItemsToPlayer(Page page, Player player) {
    for (int j = 0; j < page.getItems().size(); j++) {
      player.getItems().add(itemService.findItemByName(page.getItems().get(j).getName()));
    }
  }

  private void removeUsedItemFromPlayer(Player player, String jump) {
    for (int i = 0; i < player.getItems().size(); i++) {
      if (player.getItems().get(i).getName().equals(getJumpPage(jump).getUsedItem())) {

        if (player.getItems().get(i).getAmount() - 1 != 0){
          Item item = player.getItems().get(i);
          item.setAmount(player.getItems().get(i).getAmount() - 1);
          itemService.editItem(item);
        }else {
          itemService.deleteItem(player.getItems().get(i).getId());
          player.getItems().remove(i);
        }
        break;
      }
    }
  }

  public Page getCurrentPageFromPlayer(Player player) {
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
