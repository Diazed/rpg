package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;

public interface Parser {

  boolean parse(Game game, String line);

}
