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

    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public String playingTheGame(Model model, Principal principal){

        String name = principal.getName();
        //Player player = playerService.getRequestedPlayer(name);
        //PlayerDTO playerDTO = playerDTOConverter.toDTO(player);
        //model.addAttribute("playerDTO", playerDTO);
        Game game = Parser.parser();





        return "homepage/index";
    }

}
