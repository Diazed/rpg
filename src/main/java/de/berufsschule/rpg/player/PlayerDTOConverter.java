package de.berufsschule.rpg.player;

import de.berufsschule.rpg.game.Parser;
import org.springframework.stereotype.Component;

@Component
public class PlayerDTOConverter {

    private Parser parser;

    public PlayerDTOConverter(Parser parser){

        this.parser = parser;
    }

    public Player toModel(PlayerDTO dto){

        Player model = new Player();

        model.setLevel(dto.getLevel());
        model.setPassword(dto.getPassword());
        model.setUsername(dto.getUsername());


        return model;
    }

    public PlayerDTO toDTO(Player model){
        PlayerDTO dto = new PlayerDTO();

        dto.setUsername(model.getUsername());
        dto.setLevel(model.getLevel());
        dto.setPassword(model.getPassword());




        return dto;
    }

}
