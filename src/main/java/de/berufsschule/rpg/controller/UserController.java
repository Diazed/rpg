package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  UserDTOConverter userDTOConverter;
  UserService userService;

  @Autowired
  public UserController(UserDTOConverter userDTOConverter, UserService userService) {
    this.userDTOConverter = userDTOConverter;
    this.userService = userService;
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String openRegisterPage(Model model) {

    UserDTO emptyDTO = new UserDTO();
    model.addAttribute("userDTO", emptyDTO);

    return "homepage/register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String registerNewPlayer(UserDTO userDTO, BindingResult bindingResult) {

    User user = userDTOConverter.toModel(userDTO);

    userService.registerUser(user, bindingResult);
    if (bindingResult.hasErrors())
      return "homepage/register";
    return "homepage/index";
  }

}
