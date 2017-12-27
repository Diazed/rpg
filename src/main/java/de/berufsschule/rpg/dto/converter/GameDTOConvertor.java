package de.berufsschule.rpg.dto.converter;

import de.berufsschule.rpg.dto.GameDTO;
import de.berufsschule.rpg.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameDTOConvertor {

  private PlayerDTOConverter playerDTOConverter;
  private GamePlanDTOConverter gamePlanDTOConverter;

  @Autowired
  public GameDTOConvertor(PlayerDTOConverter playerDTOConverter,
      GamePlanDTOConverter gamePlanDTOConverter) {
    this.playerDTOConverter = playerDTOConverter;
    this.gamePlanDTOConverter = gamePlanDTOConverter;
  }


  public GameDTO toDTO(Game model) {

    GameDTO dto = new GameDTO();

    dto.setUserId(model.getUserId());
    dto.setPlayer(playerDTOConverter.toDTO(model.getPlayer()));
    dto.setGameplan(gamePlanDTOConverter.toDTO(model.getGamePlan()));
    dto.setId(model.getId());

    return dto;
  }

}
