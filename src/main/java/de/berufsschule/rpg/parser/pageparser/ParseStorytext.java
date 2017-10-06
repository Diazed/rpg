package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ParseStorytext extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#STORYTEXT")) {
      line = "";
      StringBuilder storyTextBuilder = new StringBuilder();
      while (!line.contains("#")) {
        line = getNextLine(fileIn);
        if (!line.contains("#")) {
          if (!line.endsWith(" "))
            line += " ";
          line = splitLongWords(line);
          storyTextBuilder.append(line);
        }
      }
      Page page = getLastCreatedPage(gamePlan);
      page.setStorytext(storyTextBuilder.toString());
      return true;
    }
    return false;
  }

  private String splitLongWords(String line){

    String[] words = line.split(" ");

    for (int i=0; i<words.length; i++){
      StringBuilder newWordBuilder = new StringBuilder();
      String word = words[i];
      boolean start = true;
      while (word.length() > 25){
        if (start){
          newWordBuilder.append(word.substring(0, 25));
        }else {
          newWordBuilder.append("- ");
          newWordBuilder.append(word.substring(0, 25));
        }
        start = false;
        word = word.substring(25, word.length());
        if (word.length() < 25){
          newWordBuilder.append("- ");
          newWordBuilder.append(word);
        }
      }
      words[i] += newWordBuilder.toString();
    }
    StringBuilder resultBuilder = new StringBuilder();
    for (String word : words) {
      resultBuilder.append(word);
      resultBuilder.append(" ");
    }

    return resultBuilder.toString();
  }
}
