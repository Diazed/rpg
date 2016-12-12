package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Decision {

    private String jump;
    private String text;
    private Item item = new Item();
}
