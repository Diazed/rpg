package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomepageController {

  UserService userService;
  UserDTOConverter userDTOConverter;

  @Autowired
  public HomepageController(UserService userService, UserDTOConverter userDTOConverter) {
    this.userService = userService;
    this.userDTOConverter = userDTOConverter;
  }

  @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
  public String toHomepage(Principal principal, Model model) {
    UserDTO userDTO = new UserDTO();
    if (principal != null) {
      User user = userService.getRequestedUser(principal.getName());
      userDTO = userDTOConverter.toDto(user);
    }
    model.addAttribute("userDTO", userDTO);
    return "homepage/index";
  }

}
