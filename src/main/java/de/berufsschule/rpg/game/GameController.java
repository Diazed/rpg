package de.berufsschule.rpg.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GameController {

  @RequestMapping(value = "/play", method = RequestMethod.GET)
  public String test(){
    return "test";
  }

}
