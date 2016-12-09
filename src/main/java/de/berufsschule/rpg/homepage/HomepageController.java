package de.berufsschule.rpg.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomepageController {

  @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
  public String test(){
    return "homepage/index";
  }

}
