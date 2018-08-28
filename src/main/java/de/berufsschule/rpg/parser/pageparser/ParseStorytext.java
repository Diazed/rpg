package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseStorytext extends BaseParser implements PageParser {

  @Override
  public boolean parsePage(ParseModel parseModel) {
    if (checkCommand(parseModel, Command.STORYTEXT)) {
      StringBuilder storyTextBuilder = new StringBuilder();
      String line = "";
      while (!line.contains("#")) {

        Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
        if (optionalNextLine.isPresent()){
          line = optionalNextLine.get();
          if (!line.contains("#")) {
            if (!line.endsWith(" "))
              line += " ";
            line = splitLongWords(line);
            storyTextBuilder.append(line);
          }
        }
      }
      Page page = getLastCreatedPage(parseModel.getGamePlan());
      page.setStorytext(storyTextBuilder.toString());
      return true;
    }
    return false;
  }

  private void formatTextLine(StringBuilder storyTextBuilder, String line) {
    if (!line.contains("#")) {
      if (!line.endsWith(" ")) {
        line += " ";
      }
      line = splitLongWords(line);
      storyTextBuilder.append(line);
    }
  }

  private String splitLongWords(String line) {

    String[] words = line.split(" ");

    for (int i = 0; i < words.length; i++) {
      StringBuilder newWordBuilder = new StringBuilder();
      String word = words[i];
      boolean start = true;
      while (word.length() > 25) {
        if (start) {
          newWordBuilder.append(word, 0, 25);
        } else {
          newWordBuilder.append("- ");
          newWordBuilder.append(word, 0, 25);
        }
        start = false;
        word = word.substring(25);
        if (word.length() < 25) {
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
