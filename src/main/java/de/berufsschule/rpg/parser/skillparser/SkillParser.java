package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.model.GamePlan;

import java.util.Scanner;

public interface SkillParser {
    boolean parseSkill(GamePlan gamePlan, String line, Scanner fileIn);
}
