package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.dto.PlayerDTO;
import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.converter.PlayerDTOConverter;
import de.berufsschule.rpg.dto.converter.UserDTOConverter;
import de.berufsschule.rpg.model.Game;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.GameService;
import de.berufsschule.rpg.services.ItemService;
import de.berufsschule.rpg.services.SkillService;
import de.berufsschule.rpg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ProfileController {

  private GameService gameService;
  private UserService userService;
  private ItemService itemService;
  private SkillService skillService;
  private PlayerDTOConverter playerDTOConverter;
  private UserDTOConverter userDTOConverter;

  @Autowired
  public ProfileController(GameService gameService, PlayerDTOConverter playerDTOConverter,
      UserService userService, SkillService skillService, ItemService itemService,
      UserDTOConverter userDTOConverter) {
    this.gameService = gameService;
    this.skillService = skillService;
    this.playerDTOConverter = playerDTOConverter;
    this.userService = userService;
    this.itemService = itemService;
    this.userDTOConverter = userDTOConverter;
  }


  @RequestMapping(value = "/profile/{gameplanId}", method = RequestMethod.GET)
  public String openPlayerProfile(@PathVariable Integer gameplanId, Model model, Principal principal) {

    UserDTO userDTO;
    User user = userService.findByEmail(principal.getName());
    userDTO = userDTOConverter.toDto(user);

    model.addAttribute("userDTO", userDTO);
    Game game = gameService.loadGame(gameplanId, user);
    if (game == null){
      return "redirect:/games";
    }
    Player currentPlayer = game.getPlayer();
    PlayerDTO playerDTO = playerDTOConverter.toDTO(currentPlayer);
    model.addAttribute("items", itemService.createInventory(currentPlayer));
    model.addAttribute("playerDTO", playerDTO);
    model.addAttribute("gameplanId", game.getGamePlan().getId());

    return "game/profile";
  }

  @RequestMapping(value = "/profile/", method = RequestMethod.GET)
  public String noGameName() {
    return "redirect:/games";
  }

  @RequestMapping(value = "/profile/{gameplanId}/item/{itemId}", method = RequestMethod.POST)
  public String useItem(@PathVariable Integer itemId, @PathVariable Integer gameplanId,
      Principal principal) {

    User user = userService.findByEmail(principal.getName());
    Game game = gameService.loadGame(gameplanId, user);
    Player currentPlayer = game.getPlayer();
    itemService.useItem(itemService.getItem(itemId), currentPlayer);
    return "redirect:/profile/" + game.getGamePlan().getId();
  }

  @RequestMapping(value = "/profile/{gameplanId}/skill/{skillId}", method = RequestMethod.POST)
  public String useSkillPoint(@PathVariable Integer skillId, @PathVariable Integer gameplanId,
      Principal principal) {

    User user = userService.findByEmail(principal.getName());
    Game game = gameService.loadGame(gameplanId, user);
    if (game == null)
      return "redirect:/games";
    Player currentPlayer = game.getPlayer();

    skillService.useSkillPoint(skillId, currentPlayer);
    return "redirect:/profile/" + game.getGamePlan().getId();
  }

}
