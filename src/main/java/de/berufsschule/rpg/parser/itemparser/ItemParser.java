package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface ItemParser {
  boolean parseItem(GamePlan gamePlan, String line, Scanner fileIn);
}
