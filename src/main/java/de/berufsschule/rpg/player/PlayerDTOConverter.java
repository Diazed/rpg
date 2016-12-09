package de.berufsschule.rpg.player;

import de.berufsschule.rpg.game.Game;
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
        model.setUsername(dto.getUsername());
        model.setPage(dto.getPage().getName());


        return model;
    }

    public PlayerDTO toDTO(Player model){
        PlayerDTO dto = new PlayerDTO();

        dto.setUsername(model.getUsername());
        dto.setLevel(model.getLevel());

        Game game = Parser.parser();
        for (int i=0; i<game.getPages().size(); i++){
            if (game.getPages().get(i).getName() == model.getPage()){
                dto.setPage(game.getPages().get(i));
            }
        }

        return dto;
    }

}
