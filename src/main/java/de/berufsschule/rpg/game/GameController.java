package de.berufsschule.rpg.game;

import de.berufsschule.rpg.player.Player;
import de.berufsschule.rpg.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class GameController {

  PlayerService playerService;

  public GameController(PlayerService playerService){
    this.playerService = playerService;
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String test(Model model, Principal principal){

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());

    Game game = Parser.parser();

    Page page = new Page();

    int size = game.getPages().size();
    for (int i=0; i<size; i++){
      if (game.getPages().get(i).getName() == loggedInPlayer.getLevel()){
        page = game.getPages().get(i);
      }
    }



    model.addAttribute("page", page);

    return "test";
  }

}
