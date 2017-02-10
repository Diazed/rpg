package de.berufsschule.rpg.parser.pageparser.decisionparser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface DecisionParser {
  boolean parseDecision(GamePlan gamePlan, String line, Scanner fileIn);
}
