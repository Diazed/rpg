package de.berufsschule.rpg.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    private Integer id;
    private String username;
    private String password;
    private String level;

}
