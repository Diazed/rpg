package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;

import de.berufsschule.rpg.model.ParseModel;
import java.util.Scanner;

public interface GamePlanParser {

  boolean parseGamePlan(ParseModel parseModel);
}
