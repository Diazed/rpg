package de.berufsschule.rpg.game;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page {

    private String name;
    private String storytext;
    private List<Decision> decisions;

}
