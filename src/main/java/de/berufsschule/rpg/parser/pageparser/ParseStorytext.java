package de.berufsschule.rpg.parser.pageparser;

import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.BaseParser;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParseStorytext extends BaseParser implements PageParser {

  private final static int MAX_WORD_LENGTH = 25;

  @Override
  public boolean parsePage(ParseModel parseModel) {

    if (checkCommand(parseModel, Command.STORYTEXT)) {

      StringBuilder storyTextBuilder = new StringBuilder();
      String line = "";

      //Run till the end signal '#'
      while (!line.equals("#")) {

        Optional<String> optionalNextLine = parseModel.getAndSetNextLine();

        if (optionalNextLine.isPresent()){
          line = optionalNextLine.get();
          if (!line.equals("#")) {
            line = endingWhitespace(line);
            line = splitLongWords(line);
            storyTextBuilder.append(line);
          }
        }
      }
      Page page = getLastCreatedPage(parseModel.getGamePlan());
      page.setStorytext(storyTextBuilder.toString().trim());
      return true;
    }
    return false;
  }

  // Avoiding connection of two different lines.
  private String endingWhitespace(String line) {
    if (!line.endsWith(" "))
      line += " ";
    return line;
  }

  private String reuniteString(String[] words){
    StringBuilder resultBuilder = new StringBuilder();
    for (String word : words) {
      resultBuilder.append(word);
      resultBuilder.append(" ");
    }
    return resultBuilder.toString();
  }

  private void handleRestOfWord(String word, StringBuilder wordBuilder) {
    if (word.length() < MAX_WORD_LENGTH) {
      wordBuilder.append("- ");
      wordBuilder.append(word);
    }
  }

  private String shortenWord(String word, StringBuilder wordBuilder, Boolean start) {
    if (!start) wordBuilder.append("- ");
    wordBuilder.append(word, 0, MAX_WORD_LENGTH);
    word = word.substring(MAX_WORD_LENGTH);
    handleRestOfWord(word, wordBuilder);
    return word;
  }

  private String splitLongWords(String line) {

    // Get all single words
    String[] words = line.split(" ");
    // Set flag for first word only.
    // This is to avoid adding '- ' in front of the word
    boolean start = true;
    //Iterate all words
    for (int i = 0; i < words.length; i++) {
      // Get current word
      String word = words[i];
      //Check if word is too long
      if (word.length() > MAX_WORD_LENGTH){
        StringBuilder wordBuilder = new StringBuilder();
        while (word.length() > MAX_WORD_LENGTH){
          word = shortenWord(word, wordBuilder, start);
          start = false;
        }
        // Replace long word with split version
        words[i] = wordBuilder.toString();
      }
    }
    return reuniteString(words);
  }
}
