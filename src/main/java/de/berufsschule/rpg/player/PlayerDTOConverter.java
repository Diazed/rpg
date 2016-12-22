package de.berufsschule.rpg.player;

import org.springframework.stereotype.Component;

@Component
public class PlayerDTOConverter {



    public PlayerDTOConverter(){


    }

    public Player toModel(PlayerDTO dto){

        Player model = new Player();

        model.setPosition(dto.getPosition());

        model.setPassword(dto.getPassword());
        model.setUsername(dto.getUsername());
        model.setPlayerLvl(dto.getPlayerLvl());
        model.setExp(dto.getExp());
        model.setHitpoints(dto.getHitpoints());
        model.setThirst(dto.getThirst());
        model.setHunger(dto.getHunger());
        model.setItems(dto.getItems());
        model.setCheckpoint(dto.getCheckpoint());

        return model;
    }

    public PlayerDTO toDTO(Player model){
        PlayerDTO dto = new PlayerDTO();

        dto.setPosition(model.getPosition());
        dto.setUsername(model.getUsername());

        dto.setPassword(model.getPassword());
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
