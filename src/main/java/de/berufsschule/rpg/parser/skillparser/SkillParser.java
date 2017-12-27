package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.model.GamePlan;

import de.berufsschule.rpg.model.ParseModel;
import java.util.Scanner;

public interface SkillParser {

  boolean parseSkill(ParseModel parseModel);
}
