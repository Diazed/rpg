package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;

import java.util.Scanner;

public interface Parser {

  boolean parse(Game game, String line, Scanner fileIn);

}
