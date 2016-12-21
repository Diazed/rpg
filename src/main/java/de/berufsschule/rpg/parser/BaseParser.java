package de.berufsschule.rpg.parser;

import org.springframework.stereotype.Component;

@Component
public abstract class BaseParser implements Parser{

  public String getStringBetweenQuotationMarks(String line) {
    String string = "KEIN TEXT IN \"\" ANGEGEBEN";
    line = line.replace("\t", "");
    line = line.replace("'", "");

    return line;
  }

}
