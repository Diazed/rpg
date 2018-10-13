package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.tools.Command;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

public abstract class SubParserRunner<SP> extends BaseParser {

  protected abstract Command getStartCommand();

  protected abstract Command getEndCommand();

  protected abstract BiFunction<SP, ParseModel, Boolean> getParseMethod();

  protected abstract List<SP> getSubParser();

  protected abstract void saveLastCreated(ParseModel parseModel);

  protected boolean parse(ParseModel parseModel) {
    if (checkCommand(parseModel, getStartCommand())) {
      return loopThroughLines(parseModel);
    }
    return false;
  }

  private boolean loopThroughLines(ParseModel parseModel) {
    while (!checkCommand(parseModel, getEndCommand())) {
      Optional<String> optionalNextLine = parseModel.getAndSetNextLine();
      if (optionalNextLine.isPresent()) {
        runEachParser(parseModel, optionalNextLine.get());
      } else {
        return false;
      }
    }
    saveLastCreated(parseModel);
    return true;
  }

  private void runEachParser(ParseModel parseModel, String line) {

    if (validateLine(line)) {
      for (SP subParser : getSubParser()) {
        if (getParseMethod().apply(subParser, parseModel)) {
          break;
        }
      }
    }
  }

  private boolean validateLine(String line) {
    return !line.startsWith("//") && !Objects.equals(line, "");
  }

}
