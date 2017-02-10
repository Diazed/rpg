package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface GamePlanParser {
  boolean parseGamePlan(GamePlan gamePlan, String line, Scanner fileIn);
}
