package de.berufsschule.rpg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(min=1, max=30)
    private String username;
    @NotNull
    @Size(min=1, max=30)
    private String password;
    private String checkpoint;

    private HashMap<String, String> position;
    private HashMap<String, Boolean> liveStatusInGame;

    private Integer playerLvl;
    private Integer hitpoints;
    private Integer hunger;
    private Integer thirst;
    private Integer exp;
    private String role = "ROLE_USER";
    private boolean enabled = true;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
}
