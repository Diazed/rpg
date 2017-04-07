package de.berufsschule.rpg.parser.gameplanparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.PageParser;
import de.berufsschule.rpg.parser.skillparser.SkillParser;
import de.berufsschule.rpg.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ParseSkills extends BaseParser implements GamePlanParser {
    private List<SkillParser> skillParsers;
    private SkillService skillService;

    @Autowired
    public ParseSkills(List<SkillParser> skillParsers, SkillService skillService) {
        this.skillParsers = skillParsers;
        this.skillService = skillService;
    }

    @Override
    public boolean parseGamePlan(GamePlan gamePlan, String line, Scanner fileIn) {
        if (line.contains("#SKILLS")) {
            while (!line.contains("#ENDSKILLS")) {

                if (!line.startsWith("//") && !Objects.equals("", line) && !line.contains("#SKILLS")) {
                    for (SkillParser skillParser : skillParsers) {
                        if (skillParser.parseSkill(gamePlan, line, fileIn))
                            break;
                    }
                }
                line = getNextLine(fileIn);
            }
            skillService.persistSkillsFromGamePlan(gamePlan);
            return true;
        }
        return false;
    }
}
