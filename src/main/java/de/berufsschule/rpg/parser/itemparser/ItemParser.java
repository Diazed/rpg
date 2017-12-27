package de.berufsschule.rpg.parser.itemparser;

import de.berufsschule.rpg.model.GamePlan;

import de.berufsschule.rpg.model.ParseModel;
import java.util.Scanner;

public interface ItemParser {

  boolean parseItem(ParseModel parseModel);
}
