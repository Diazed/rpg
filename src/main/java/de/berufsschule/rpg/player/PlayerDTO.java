package de.berufsschule.rpg.player;

import de.berufsschule.rpg.game.Page;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    private Integer id;
    private String username;
    private String level;
    private Page page;

}
