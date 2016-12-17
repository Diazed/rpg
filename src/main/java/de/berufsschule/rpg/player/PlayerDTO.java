package de.berufsschule.rpg.player;

import de.berufsschule.rpg.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerDTO {

    private Integer id;
    @Size(min=1, max=30)
    private String username;
    @Size(min=1, max=30)
    private String password;
    private String checkpoint;
    private String level;
    private Integer playerLvl = 0;
    private Integer hitpoints = 100;
    private Integer hunger = 0;
    private Integer thirst = 0;
    private Integer exp = 0;
    private List<Item> items = new ArrayList<>();

}
