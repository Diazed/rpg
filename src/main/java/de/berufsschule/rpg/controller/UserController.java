package de.berufsschule.rpg.controller;

import de.berufsschule.rpg.dto.UserDTO;
import de.berufsschule.rpg.dto.UserDTOConverter;
import de.berufsschule.rpg.model.User;
import de.berufsschule.rpg.services.UserService;
import java.util.Calendar;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String openRegisterPage(Model model) {

    UserDTO emptyDTO = new UserDTO();
    model.addAttribute("userDTO", emptyDTO);

    return "homepage/register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String registerNewPlayer(@Valid UserDTO userDTO, BindingResult result,
      WebRequest request) {

    User registered = new User();
    if (!result.hasErrors()) {
      registered = createUserAccount(userDTO, result);
    }
    if (registered == null) {
      result.rejectValue("email", "message.regError");
    }
    if (result.hasErrors())
      return "homepage/register";

    return "homepage/index";
  }


  private User createUserAccount(UserDTO userDTO, BindingResult result) {
    User registered = null;

    registered = userService.registerNewUserAccount(userDTO, result);

    return registered;
  }

}
