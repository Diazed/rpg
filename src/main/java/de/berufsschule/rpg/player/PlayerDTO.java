package de.berufsschule.rpg.player;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PlayerDTO {

    private Integer id;
    @Size(min=1, max=30)
    private String username;
    @Size(min=1, max=30)
    private String password;
    private String level;
    private Integer playerLvl;
    private Integer hitpoints;
    private Integer hunger;
    private Integer thirst;
    private Integer exp;

}
