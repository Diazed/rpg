package de.berufsschule.rpg.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Component
public class PlayerValidation {

  public final static String ERROR_EXISTING_NAME = "Der Name exestiert bereits.";
  private PlayerRepository playerRepository;
  private Validator validator;

  @Autowired
  public PlayerValidation(PlayerRepository playerRepository, Validator validator) {
    this.playerRepository = playerRepository;
    this.validator = validator;
  }

  public void validate(Player newPlayer, BindingResult bindingResult) {
    validator.validate(newPlayer, bindingResult);
    if (bindingResult.hasErrors()) {
      return;
    }
    Player existingPlayer = playerRepository.findByUsername(newPlayer.getUsername());
    if ((existingPlayer != null) && (existingPlayer.getId() != newPlayer.getId())) {
      bindingResult.rejectValue("username", "", ERROR_EXISTING_NAME);
    }
  }

}
