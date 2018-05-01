package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.domain.dto.converter.PlayerDTOConverter;
import de.berufsschule.rpg.domain.dto.UserDTO;
import de.berufsschule.rpg.domain.dto.converter.UserDTOConverter;
import de.berufsschule.rpg.domain.model.Game;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.User;
import de.berufsschule.rpg.services.GamePlanService;
import de.berufsschule.rpg.services.GameService;
import de.berufsschule.rpg.services.JumpService;
import de.berufsschule.rpg.services.UserService;
import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class GameController {

  private GameService gameService;
  private PlayerDTOConverter playerDTOConverter;
  private UserDTOConverter userDTOConverter;
  private UserService userService;
  private GamePlanService gamePlanService;
  private JumpService jumpService;

  @Autowired
  public GameController(GameService gameService, PlayerDTOConverter playerDTOConverter,
      UserService userService, GamePlanService gamePlanService, UserDTOConverter userDTOConverter,
      JumpService jumpService) {
    this.gameService = gameService;
    this.playerDTOConverter = playerDTOConverter;
    this.userService = userService;
    this.gamePlanService = gamePlanService;
    this.userDTOConverter = userDTOConverter;
    this.jumpService = jumpService;
  }

  @RequestMapping(value = "/play/{gamePlanId}", method = RequestMethod.GET)
  public String invokeGame(@PathVariable Integer gamePlanId, Model model, Principal principal) {

    User currentUser = userService.findByEmail(principal.getName());
    Page page = jumpService.playPage(currentUser, gamePlanId);
    if (page == null){
      return "redirect:/play";
    }

    model.addAttribute("userDTO", userDTOConverter.toDto(currentUser));
    model.addAttribute("page", page);
    Game game = gameService.getGame(currentUser.getCurrentGame());
    Player currentPlayer = game.getPlayer();
    model.addAttribute("gameplanid", gamePlanId);
    model.addAttribute("playerDTO", playerDTOConverter.toDTO(currentPlayer));
    return "game/ingame";
  }

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String noGameId() {
    return "redirect:/games";
  }

  @RequestMapping(value = "/play/{gamePlanId}/{clickedPossibilityId}", method = RequestMethod.POST)
  public String goToNextPage(@PathVariable Integer clickedPossibilityId,
      @PathVariable Integer gamePlanId, Principal principal) {

    User currentUser = userService.findByEmail(principal.getName());
    if (jumpService.jumpToNextPage(currentUser, gamePlanId, clickedPossibilityId)) {
      return "redirect:/play/" + gamePlanId;
    }
    return "redirect:/play";
  }

  @RequestMapping(value = "/games", method = RequestMethod.GET)
  public String initiateTheGame(Model model, Principal principal) {
    gameService.initiateGames();
    addUserDtoToModel(principal, model);
    model.addAttribute("games", gamePlanService.getGamePlanList());

    return "game/pickgame";
  }

  private void addUserDtoToModel(Principal principal, Model model) {
    UserDTO userDTO = new UserDTO();
    if (principal != null) {
      User user = userService.findByEmail(principal.getName());
      userDTO = userDTOConverter.toDto(user);
    }
    model.addAttribute("userDTO", userDTO);
  }

}
