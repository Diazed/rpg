package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public abstract class BaseParser implements Parser{

  public String getStringBetweenQuotationMarks(String line) {
    line = line.replace("\t", "");
    line = line.trim();

    Pattern pattern = Pattern.compile("'(.*?)'");
    Matcher matcher = pattern.matcher(line);
    if (matcher.find())
      line = matcher.group(1);
    return line;
  }

  public HashMap<String, Integer> getIndexes(Game game){

    HashMap<String, Integer> indexes = new HashMap<>();
    indexes.put("pageIndx", game.getPages().size() - 1);
    indexes.put("itemIndx", game.getPages().get(indexes.get("pageIndx")).getItems().size() - 1);
    indexes.put("decisionIndx", game.getPages().get(indexes.get("pageIndx")).getDecisions().size() - 1);

    return indexes;
  }

  public String prepareStringForParseInt(String line) {
    line = line.replace("\t", "");
    line = line.replace(" ", "");

    return line;
  }

}
