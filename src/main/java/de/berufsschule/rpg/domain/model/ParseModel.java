package de.berufsschule.rpg.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ParseModel {

  private GamePlan gamePlan;
  private String line;
  private Scanner fileIn;
  private List<Decision> uncompleteDecisions;

  public ParseModel(GamePlan gamePlan, String line, Scanner fileIn) {
    this.gamePlan = gamePlan;
    this.line = line;
    this.fileIn = fileIn;
    this.uncompleteDecisions = new ArrayList<>();
  }

  public boolean hasNext() {
    return this.fileIn.hasNext();
  }

  public boolean hasNextLine() {
    return this.fileIn.hasNextLine();
  }

  public boolean checkCompetence(String competenceIdentifier) {
    return getLine().contains(competenceIdentifier);
  }

  public String getAndSetNextLine() {
    String line = getNextLine();
    this.setLine(line);
    return line;
  }

  public String getNextLine() {
    if (fileIn.hasNextLine()) {
      return getStringBetweenQuotationMarks(fileIn.nextLine());
    }
    log.warn("No next line found in file.");
    return "";
  }

  private String getStringBetweenQuotationMarks(String line) {

    line = line.replace("\t", "");
    line = line.trim();

    Pattern wordPattern = Pattern.compile("'(.*?)'");
    Matcher wordMatcher = wordPattern.matcher(line);
    Pattern numberPattern = Pattern.compile("(\\d)*");
    Matcher numberMatcher = numberPattern.matcher(line);
    if (wordMatcher.find()) {
      line = wordMatcher.group(1);
    }

    return line;
  }
}
