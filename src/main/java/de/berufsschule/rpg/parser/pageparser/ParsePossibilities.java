package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.pageparser.possibilityparser.PossibilityParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ParsePossibilities extends BaseParser implements PageParser {

    private List<PossibilityParser> possibilityParsers;

    @Autowired
    public ParsePossibilities(List<PossibilityParser> possibilityParsers) {
        this.possibilityParsers = possibilityParsers;
    }


    @Override
    public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
        if (line.contains("POSSIBILITIES")) {
            while (!line.contains("#ENDPOSSIBILITIES")) {
                line = getNextLine(fileIn);
                if (!line.startsWith("//") && !Objects.equals(line, "")) {
                    for (PossibilityParser parser : possibilityParsers) {
                        if (parser.parsePossibility(gamePlan, line, fileIn))
                            break;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
