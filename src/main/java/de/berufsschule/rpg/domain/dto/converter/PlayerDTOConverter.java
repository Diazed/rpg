package de.berufsschule.rpg.domain.dto.converter;

import de.berufsschule.rpg.domain.dto.PlayerDTO;
import de.berufsschule.rpg.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerDTOConverter {

  public PlayerDTO toDTO(Player model) {
    PlayerDTO dto = new PlayerDTO();

    dto.setPosition(model.getPosition());
    dto.setPlayerLvl(model.getPlayerLvl());
    dto.setExp(model.getExp());
    dto.setHitpoints(model.getHitpoints());
    dto.setHunger(model.getHunger());
    dto.setThirst(model.getThirst());
    dto.setItems(model.getItems());
    dto.setCheckpoint(model.getCheckpoint());
    dto.setProgressPercentage(model.getLevelProgress());
    dto.setNeededExp(model.getNeededExp());
    dto.setSkills(model.getSkills());
    dto.setSkillPoints(model.getSkillPoints());

    return dto;
  }
}
