package de.berufsschule.rpg.game;

import de.berufsschule.rpg.parser.ParserRunner;
import de.berufsschule.rpg.player.Player;
import de.berufsschule.rpg.player.PlayerDTOConverter;
import de.berufsschule.rpg.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class GameController {

  GameService gameService;
  PlayerService playerService;
  PlayerDTOConverter playerDTOConverter;
  ParserRunner parserRunner;

  @Autowired
  public GameController(GameService gameService, PlayerService playerService, PlayerDTOConverter playerDTOConverter, ParserRunner parserRunner) {
    this.gameService = gameService;
    this.playerService = playerService;
    this.playerDTOConverter = playerDTOConverter;
    this.parserRunner = parserRunner;
  }

  @RequestMapping(value = "/play/{gamename}", method = RequestMethod.GET)
  public String invokeGame(@PathVariable String gamename,Model model, Principal principal) {

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());

    Page page = gameService.preparePage(loggedInPlayer, gamename);
    if (page == null){
      return "redirect:/play";
    }

    model.addAttribute("page", page);
    model.addAttribute("gamename", gamename);
    model.addAttribute("playerDTO", playerDTOConverter.toDTO(loggedInPlayer));
    return "game/ingame";
  }

  @RequestMapping(value = "/play/{gamename}/{jump}", method = RequestMethod.POST)
  public String goToNextPage(@PathVariable String jump,@PathVariable String gamename, Principal principal) {

    Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());
    if (!gameService.prepareJump(loggedInPlayer, jump, gamename)){
      return "redirect:/play";
    }
    return "redirect:/play/"+gamename;
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String initiateTheGame(Model model){
    gameService.initiateGame();
    model.addAttribute("games", gameService.getGameList());

    return "game/pickgame";
  }

}
