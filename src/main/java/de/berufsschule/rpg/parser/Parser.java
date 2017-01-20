package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Game;

import java.util.Scanner;

public interface Parser {

  boolean parse(Game game, String line, Scanner fileIn);

}
