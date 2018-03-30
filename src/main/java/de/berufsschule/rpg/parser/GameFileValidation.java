package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.GamePlan;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameFileValidation {

  private List<String> ignoreFiles = new ArrayList<>();

  public boolean fileIsOnIgnoreList(String filename) {

    for (String ignored : ignoreFiles) {
      if (Objects.equals(ignored, filename)) {
        return true;
      }
    }
    return false;
  }

  public boolean fileAlreadyParsed(String fileName, Map<String, GamePlan> parsedGames) {

    for (GamePlan gamePlan : parsedGames.values()) {
      if (Objects.equals(gamePlan.getFilename(), fileName)) {
        return true;
      }
    }
    return false;
  }

  public boolean validateFileInput(Scanner fileIn, String filename) {

    if (fileIn.hasNextLine()) {
      if (fileIn.nextLine().contains("#RPG")) {
        if (fileIsOnIgnoreList(filename)) {
          ignoreFiles.remove(filename);
          log.info("Removed \"" + filename + "\" from the list of ignored files.");
        }
        return true;
      } else {
        ignoreFiles.add(filename);
        log.info("No #RPG. Adding \"" + filename + "\" to the list of ignored files.");
        return false;
      }
    } else {
      ignoreFiles.add(filename);
      log.info("No lines to parse. Adding \"" + filename + "\" to the list of ignored files.");
      return false;
    }
  }
}
