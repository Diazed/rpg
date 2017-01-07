package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Page {

    private String name;
    private String storytext;
    private List<Decision> decisions;
    private List<Item> items = new ArrayList<>();
    private String usedItem = "";
    private boolean checkpoint = false;
}
