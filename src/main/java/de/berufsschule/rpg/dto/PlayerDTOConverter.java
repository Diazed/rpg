package de.berufsschule.rpg.dto;

import de.berufsschule.rpg.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerDTOConverter {



    public PlayerDTOConverter(){


    }


    public PlayerDTO toDTO(Player model){
        PlayerDTO dto = new PlayerDTO();

        dto.setPosition(model.getPosition());
        dto.setPlayerLvl(model.getPlayerLvl());
        dto.setExp(model.getExp());
        dto.setHitpoints(model.getHitpoints());
        dto.setHunger(model.getHunger());
        dto.setThirst(model.getThirst());
        dto.setItems(model.getItems());
        dto.setCheckpoint(model.getCheckpoint());

        return dto;
    }
}
