package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface Parser {

  boolean parse(GamePlan gamePlan, String line, Scanner fileIn);

}
