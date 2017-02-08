package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.services.GameService;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.dto.PlayerDTO;
import de.berufsschule.rpg.dto.PlayerDTOConverter;
import de.berufsschule.rpg.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class PlayerController {

    private GameService gameService;
    private PlayerService playerService;
    private PlayerDTOConverter playerDTOConverter;

    @Autowired
    public PlayerController(GameService gameService, PlayerService playerService, PlayerDTOConverter playerDTOConverter){
        this.gameService = gameService;
        this.playerService = playerService;
        this.playerDTOConverter = playerDTOConverter;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String openRegisterPage(Model model){

        PlayerDTO emptyDTO = new PlayerDTO();
        model.addAttribute("playerDTO", emptyDTO);

        return "homepage/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerNewPlayer(PlayerDTO playerDTO, BindingResult bindingResult){

        Player player = playerDTOConverter.toModel(playerDTO);

        playerService.registerPlayer(player, bindingResult);
        if (bindingResult.hasErrors())
            return "homepage/register";
        return "homepage/index";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String openPlayerProfile(Model model, Principal principal){

        Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());
        model.addAttribute("playerDTO", playerDTOConverter.toDTO(loggedInPlayer));

        return "game/profile";
    }


    @RequestMapping(value = "/profile/{id}", method = RequestMethod.POST)
    public String useItem(@PathVariable Integer id, Principal principal) {

        Player loggedInPlayer = playerService.getRequestedPlayer(principal.getName());
        gameService.useItem(id, loggedInPlayer);
        return "redirect:/profile";
    }
}
