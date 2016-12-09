package de.berufsschule.rpg.player;

import de.berufsschule.rpg.game.Game;
import de.berufsschule.rpg.game.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class PlayerController {

    private PlayerService playerService;
    private PlayerDTOConverter playerDTOConverter;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerDTOConverter playerDTOConverter){
        this.playerService = playerService;
        this.playerDTOConverter = playerDTOConverter;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String openRegisterPage(Model model, Principal principal){

        PlayerDTO emptyDTO = new PlayerDTO();
        model.addAttribute("playerDTO", emptyDTO);

        //String name = principal.getName();
        //Player player = playerService.getRequestedPlayer(name);
        //PlayerDTO playerDTO = playerDTOConverter.toDTO(player);
        //model.addAttribute("playerDTO", playerDTO);
        Game game = Parser.parser();
        return "homepage/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerNewPlayer(Model model, PlayerDTO playerDTO){

        Player player = playerDTOConverter.toModel(playerDTO);
        player.setLevel("start");
        playerService.registerPlayer(player);

        return "homepage/index";
    }

}
