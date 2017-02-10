package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.dto.PlayerDTOConverter;
import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.GameService;
import de.berufsschule.rpg.services.ItemService;
import de.berufsschule.rpg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class PlayerController {

    private GameService gameService;
  private UserService userService;
  private ItemService itemService;
    private PlayerDTOConverter playerDTOConverter;
  private UserDTOConverter userDTOConverter;

    @Autowired
    public PlayerController(GameService gameService, PlayerDTOConverter playerDTOConverter, UserService userService, ItemService itemService, UserDTOConverter userDTOConverter) {
        this.gameService = gameService;
        this.playerDTOConverter = playerDTOConverter;
      this.userService = userService;
      this.itemService = itemService;
      this.userDTOConverter = userDTOConverter;
    }


  @RequestMapping(value = "/profile/{gamename}", method = RequestMethod.GET)
  public String openPlayerProfile(@PathVariable String gamename, Model model, Principal principal) {
    addUserDtoToModel(principal, model);
    Player currentPlayer = gameService.getGame(gamename).getPlayer();
    model.addAttribute("playerDTO", playerDTOConverter.toDTO(currentPlayer));

        return "game/profile";
    }


  @RequestMapping(value = "/profile/{gamename}/{itemName}", method = RequestMethod.POST)
  public String useItem(@PathVariable String itemName, @PathVariable String gamename, Principal principal) {


    Player currentPlayer = gameService.getGame(gamename).getPlayer();

    itemService.useItem(itemName, currentPlayer);
        return "redirect:/profile";
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
