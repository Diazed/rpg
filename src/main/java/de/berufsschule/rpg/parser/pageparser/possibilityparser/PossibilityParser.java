package de.berufsschule.rpg.parser.pageparser.possibilityparser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface PossibilityParser {

  boolean parsePossibility(GamePlan gamePlan, String line, Scanner fileIn);
}
