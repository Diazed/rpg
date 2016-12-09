package de.berufsschule.rpg.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String openRegisterPage(Model model){

        PlayerDTO emptyDTO = new PlayerDTO();
        model.addAttribute("playerDTO", emptyDTO);

        return "homepage/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerNewPlayer(Model model, PlayerDTO playerDTO, BindingResult bindingResult){

        Player player = playerDTOConverter.toModel(playerDTO);
        player.setLevel("start");
        playerService.registerPlayer(player, bindingResult);
        if (bindingResult.hasErrors())
            return "homepage/register";
        return "homepage/index";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String openPlayerProfile(Model model){

        return "";
    }

}
