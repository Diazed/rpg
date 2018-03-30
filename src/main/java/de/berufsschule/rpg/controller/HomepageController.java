package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.domain.dto.UserDTO;
import de.berufsschule.rpg.domain.dto.converter.UserDTOConverter;
import de.berufsschule.rpg.domain.model.User;
import de.berufsschule.rpg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomepageController {

  private UserService userService;
  private UserDTOConverter userDTOConverter;

  @Autowired
  public HomepageController(UserService userService, UserDTOConverter userDTOConverter) {
    this.userService = userService;
    this.userDTOConverter = userDTOConverter;
  }

  @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
  public String toHomepage(Principal principal, Model model) {
    UserDTO userDTO = new UserDTO();
    if (principal != null) {
      User user = userService.findByEmail(principal.getName());
      userDTO = userDTOConverter.toDto(user);
      if (user.isFirstVisit()) {
        user.setFirstVisit(false);
        userService.editUser(user);
      }
    }
    model.addAttribute("userDTO", userDTO);
    return "homepage/index";
  }

}
