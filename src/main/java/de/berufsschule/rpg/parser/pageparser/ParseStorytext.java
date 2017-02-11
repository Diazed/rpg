package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.parser.BaseParser;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ParseStorytext extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#STORYTEXT")) {
      String storytext = "";
      while (!line.contains("#ENDTEXT")) {
        line = getNextLine(fileIn);
        if (!line.contains("#ENDTEXT")) {
          if (!line.endsWith(" "))
            line += " ";
          line = splitLongWords(line);
          storytext += line;
        }
      }
      Page page = getLastCreatedPage(gamePlan);
      page.setStorytext(storytext);
      return true;
    }
    return false;
  }

  private String splitLongWords(String line){

    String[] words = line.split(" ");

    for (int i=0; i<words.length; i++){
      String word = words[i];
      String newWord = "";
      boolean start = true;
      while (word.length() > 25){
        if (start){
          newWord += word.substring(0, 25);
        }else {
          newWord += "- "+word.substring(0, 25);
        }
        start = false;
        word = word.substring(25, word.length());
        if (word.length() < 25){
          newWord += "- "+word;
        }

      }
      words[i] += newWord;
    }
    String result = "";
    for (int i=0; i<words.length; i++){
      result += words[i]+" ";
    }

    return result;
  }
}
