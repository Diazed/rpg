package de.berufsschule.rpg.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.Option;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ParseModel {

  private GamePlan gamePlan;
  @Getter(AccessLevel.PRIVATE)
  @Setter(AccessLevel.PRIVATE)
  private String line = "";
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Scanner fileIn;
  private List<Decision> uncompleteDecisions;

  public ParseModel(GamePlan gamePlan, String line, Scanner fileIn) {
    this.gamePlan = gamePlan;
    this.line = line;
    this.fileIn = fileIn;
    this.uncompleteDecisions = new ArrayList<>();
  }

  public boolean hasNextLine() {
    return this.fileIn.hasNextLine();
  }

  public String getCurrentLine() {
    return getLine();
  }

  public boolean gotoNextLine() {
    if (fileIn.hasNextLine()) {
      setLine(getStringBetweenQuotationMarks(fileIn.nextLine()));
      return true;
    } else {
      log.warn("No next line found in file.");
      return false;
    }
  }

  public Optional<String> getAndSetNextLine() {
    if (gotoNextLine()){
      return Optional.of(getCurrentLine());
    } else {
      return Optional.empty();
    }
  }

  private String getStringBetweenQuotationMarks(String lineToTrim) {

    lineToTrim = lineToTrim.replace("\t", "");
    lineToTrim = lineToTrim.trim();

    Pattern wordPattern = Pattern.compile("'(.*?)'");
    Matcher wordMatcher = wordPattern.matcher(lineToTrim);
    if (wordMatcher.find()) {
      lineToTrim = wordMatcher.group(1);
    }

    return lineToTrim;
  }
}
