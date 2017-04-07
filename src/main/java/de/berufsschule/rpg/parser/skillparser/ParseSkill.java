package de.berufsschule.rpg.parser.skillparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseSkill extends BaseParser implements SkillParser{
    @Override
    public boolean parseSkill(GamePlan gamePlan, String line, Scanner fileIn) {
        if (line.contains("#SKILL")){
            gamePlan.getSkills().add(new Skill());
            return true;
        }
        return false;
    }
}
