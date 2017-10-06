package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.gameplanparser.GamePlanParser;
import de.berufsschule.rpg.services.FileService;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Slf4j
public class ParserRunner {

  private HashMap<String, GamePlan> games = new HashMap<>();
  private List<GamePlanParser> gamePlanParsers;
  private GameFileValidation gameFileValidation;
  private FileService fileService;

  @Autowired
  public ParserRunner(List<GamePlanParser> gamePlanParsers, FileService fileService,
      GameFileValidation gameFileValidation) {
    this.gamePlanParsers = gamePlanParsers;
    this.fileService = fileService;
    this.gameFileValidation = gameFileValidation;
  }

  public void parseAllGames() {
    List<String> fileNames = fileService.getFileNames();
    for (String fileName : fileNames) {
      if (!gameFileValidation.fileAlreadyParsed(fileName, games) && !gameFileValidation
          .fileIsOnIgnoreList(fileName)) {
        parse(fileName);
      }
    }
  }

  public void parse(String filename) {

    File fileToParse = fileService.getGameByFileName(filename);
    try (Scanner fileIn = new Scanner(fileToParse)) {

      if (!gameFileValidation.validateFileInput(fileIn, filename)) {
        return;
      }

      GamePlan gamePlan = new GamePlan();
      gamePlan.setFilename(filename);
      Long start = System.currentTimeMillis();
      runParser(gamePlan, fileIn);
      String gamename = gamePlan.getName();
      if (games.get(gamename) != null) {
        games.remove(gamename);
        games.put(gamename, gamePlan);
      } else {
        games.put(gamename, gamePlan);
      }
      Long end = System.currentTimeMillis();
      log.info("Parsed " + filename + " in " + (end - start) + "ms.");
      fileIn.close();
    } catch (IOException e) {
      log.error("Failed to parse file (" + filename + "). Error: " + e.getMessage());
    }
  }

  private void runParser(GamePlan gamePlan, Scanner fileIn) {

    while (fileIn.hasNext()) {
      String line = fileIn.nextLine();
      if (!line.startsWith("//") && !Objects.equals(line, "")) {
        for (GamePlanParser parser : gamePlanParsers) {
          if (parser.parseGamePlan(gamePlan, line, fileIn)) {
            break;
          }
        }
      }
    }
  }
}
