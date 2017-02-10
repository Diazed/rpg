package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.dto.PlayerDTOConverter;
import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.GamePlanService;
import de.berufsschule.rpg.services.GameService;
import de.berufsschule.rpg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class GameController {

  private GameService gameService;
  private PlayerDTOConverter playerDTOConverter;
  private UserDTOConverter userDTOConverter;
  private UserService userService;
  private GamePlanService gamePlanService;

  @Autowired
  public GameController(GameService gameService, PlayerDTOConverter playerDTOConverter, UserService userService, GamePlanService gamePlanService, UserDTOConverter userDTOConverter) {
    this.gameService = gameService;
    this.playerDTOConverter = playerDTOConverter;
    this.userService = userService;
    this.gamePlanService = gamePlanService;
    this.userDTOConverter = userDTOConverter;
  }

  @RequestMapping(value = "/play/{gamename}", method = RequestMethod.GET)
  public String invokeGame(@PathVariable String gamename,Model model, Principal principal) {


    User currentUser = userService.getRequestedUser(principal.getName());
    Player currentPlayer = gameService.getGame(gamename).getPlayer();
    Page page = gameService.preparePage(currentUser, gamename);
    if (page == null){
      return "redirect:/play";
    }

    model.addAttribute("userDTO", userDTOConverter.toDto(currentUser));
    model.addAttribute("page", page);
    model.addAttribute("gamename", gamename);
    model.addAttribute("playerDTO", playerDTOConverter.toDTO(currentPlayer));
    return "game/ingame";
  }

  @RequestMapping(value = "/play/{gamename}/{jump}", method = RequestMethod.POST)
  public String goToNextPage(@PathVariable String jump,@PathVariable String gamename, Principal principal) {

    User currentUser = userService.getRequestedUser(principal.getName());
    if (gameService.prepareJump(currentUser, jump, gamename)) {
      return "redirect:/play/"+gamename;
    }
    return "redirect:/play";
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String initiateTheGame(Model model, Principal principal) {
    gameService.initiateGames();
    addUserDtoToModel(principal, model);
    model.addAttribute("games", gamePlanService.getGamePlanList());

    return "game/pickgame";
  }

  private void addUserDtoToModel(Principal principal, Model model) {
    UserDTO userDTO = new UserDTO();
    if (principal != null) {
      User user = userService.getRequestedUser(principal.getName());
      userDTO = userDTOConverter.toDto(user);
    }
    model.addAttribute("userDTO", userDTO);
  }

}
