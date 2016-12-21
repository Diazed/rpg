package de.berufsschule.rpg.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public abstract class BaseParser implements Parser{

  public String getStringBetweenQuotationMarks(String line) {
    line = line.replace("\t", "");

    Pattern pattern = Pattern.compile("'(.*?)'");
    Matcher matcher = pattern.matcher(line);
    if (matcher.find())
      line = matcher.group(1);



    return line;
  }

  public String prepareStringForParseInt(String line) {
    line = line.replace("\t", "");
    line = line.replace(" ", "");

    return line;
  }

}
