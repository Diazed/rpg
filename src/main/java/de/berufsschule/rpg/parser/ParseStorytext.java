package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Component
public class ParseStorytext extends BaseParser{
  @Override
  public boolean parse(GamePlan gamePlan, String line, Scanner fileIn) {
    if (line.contains("#STORYTEXT")) {
      String storytext = "";
      while (!line.contains("#TEND")){
        if (fileIn.hasNextLine()) {
          line = fileIn.nextLine();

          if (!line.contains("#TEND")){
            line = getStringBetweenQuotationMarks(line);
            if (!line.endsWith(" "))
              line += " ";

            line = splitLongWords(line);

            storytext += line;
          }

        }
      }
      HashMap<String, Integer> indexes = getIndexes(gamePlan);
      Page latestPage = gamePlan.getPages().get(indexes.get("pageIndx"));
      latestPage.setStorytext(storytext);
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
