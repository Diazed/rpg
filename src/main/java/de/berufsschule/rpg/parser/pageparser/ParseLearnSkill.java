package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseLearnSkill extends BaseParser implements PageParser{
    @Override
    public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
        if (line.contains("#LEARNSKILL")){
            line = getNextLine(fileIn);
            Page page = getLastCreatedPage(gamePlan);
            page.getSkills().add(line);
            return true;
        }
        return false;
    }
}
