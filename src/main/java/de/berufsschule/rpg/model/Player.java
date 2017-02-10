package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Getter
@Setter
public class Player
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String checkpoint;

    private HashMap<String, String> position;
    private HashMap<String, Boolean> liveStatusInGame;

    private Integer playerLvl;
    private Integer hitpoints;
    private Integer hunger;
    private Integer thirst;
    private Integer exp;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
}
