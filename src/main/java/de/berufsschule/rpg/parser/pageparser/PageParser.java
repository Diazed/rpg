package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface PageParser {
  boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn);
}
