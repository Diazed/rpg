package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.parser.gameplanparser.GamePlanParser;
import de.berufsschule.rpg.parser.tools.GameFileValidation;
import de.berufsschule.rpg.services.FileService;
import de.berufsschule.rpg.services.GamePlanService;
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
  private GamePlanService gamePlanService;
  private FileService fileService;

  @Autowired
  public ParserRunner(
      List<GamePlanParser> gamePlanParsers,
      GameFileValidation gameFileValidation,
      GamePlanService gamePlanService, FileService fileService) {
    this.gamePlanParsers = gamePlanParsers;
    this.gameFileValidation = gameFileValidation;
    this.gamePlanService = gamePlanService;
    this.fileService = fileService;
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

  private void parse(String filename) {

    File fileToParse = fileService.getGameByFileName(filename);
    try (Scanner fileIn = new Scanner(fileToParse)) {

      if (!gameFileValidation.validateFileInput(fileIn, filename)) {
        return;
      }

      GamePlan gamePlan = new GamePlan();
      gamePlan.setFilename(filename);
      Long start = System.currentTimeMillis();
      GamePlan parsedGamePlan = runParser(new ParseModel(gamePlan, "", fileIn));
      String gamename = parsedGamePlan.getName();
      if (games.get(gamename) != null) {
        Integer origId = games.get(gamename).getId();
        games.remove(gamename);
        parsedGamePlan.setId(origId);
        gamePlanService.save(parsedGamePlan);
        games.put(gamename, parsedGamePlan);
      } else {
        games.put(gamename, parsedGamePlan);
        gamePlanService.save(parsedGamePlan);
      }
      Long end = System.currentTimeMillis();
      log.info("Parsed " + filename + " in " + (end - start) + "ms.");
      fileIn.close();
    } catch (IOException e) {
      log.error("Failed to parse file (" + filename + "). Error: " + e.getMessage());
    }
  }

  private GamePlan runParser(ParseModel parseModel) {

    while (parseModel.hasNext()) {
      String line = parseModel.getFileIn().nextLine();
      parseModel.setLine(line);
      if (!line.startsWith("//") && !Objects.equals(line, "")) {
        for (GamePlanParser parser : gamePlanParsers) {
          if (parser.parseGamePlan(parseModel)) {
            break;
          }
        }
      }
    }
    return parseModel.getGamePlan();
  }
}
