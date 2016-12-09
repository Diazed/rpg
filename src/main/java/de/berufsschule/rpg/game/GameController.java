package de.berufsschule.rpg.game;

import de.berufsschule.rpg.player.Player;
import de.berufsschule.rpg.player.PlayerService;
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

  public GameController(PlayerService playerService){
    this.playerService = playerService;
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String spielAufrufen(Model model, Principal principal){

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());

    Parser parser = new Parser();
    Game game = parser.parser();
    Page page = new Page();
    int size = game.getPages().size();
    for (int i=0; i<size; i++){
      if (Objects.equals(game.getPages().get(i).getName(), loggedInPlayer.getLevel())){

        page = game.getPages().get(i);
      }
    }
    model.addAttribute("page", page);

    return "game/ingame";
  }

  @RequestMapping(value = "/play/{jump}", method = RequestMethod.POST)
  public String playerLevelAnpassen(@PathVariable String jump, Principal principal){

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());

    Parser parser = new Parser();
    Game game = parser.parser();
    Page page = new Page();
    int size = game.getPages().size();
    for (int i=0; i<size; i++){
      if (Objects.equals(game.getPages().get(i).getName(), loggedInPlayer.getLevel())){
        page = game.getPages().get(i);
        break;
      }
    }

    for (int i=0; i<page.getDecisions().size(); i++){
      if (Objects.equals(page.getDecisions().get(i).getJump(), jump)){
        loggedInPlayer.setLevel(jump);
        playerService.editPlayer(loggedInPlayer, loggedInPlayer.getId());
        break;
      }
    }

    return "redirect:/play";
  }

}
