package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.parser.gameplanparser.GamePlanParser;
import de.berufsschule.rpg.services.FileService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ParserRunner {

  HashMap<String, GamePlan> games = new HashMap<String, GamePlan>();

  List<GamePlanParser> gamePlanParsers;
  FileService fileService;

  @Autowired
  public ParserRunner(List<GamePlanParser> gamePlanParsers, FileService fileService) {
    this.gamePlanParsers = gamePlanParsers;
    this.fileService = fileService;
  }

  public void parseAllGames(){
    List<String> fileNames = fileService.getFileNames();
    for (String fileName : fileNames) {
      if (!gameAlreadyParsed(fileName))
        parse(fileName);
    }
  }

  private boolean gameAlreadyParsed(String fileName) {
    for (GamePlan gamePlan : games.values()) {
      if (Objects.equals(gamePlan.getFilename(), fileName))
        return true;
    }
    return false;
  }

  public void parse(String filename) {

    File fileToParse = fileService.getGameByFileName(filename);

    try (Scanner fileIn = new Scanner(fileToParse)) {
      if (fileIn.hasNextLine()){
        if (!fileIn.nextLine().contains("#RPG")){
          return;
        }
      }else {
        return;
      }
      GamePlan gamePlan = new GamePlan();
      gamePlan.setFilename(filename);
      gamePlan.setPages(new ArrayList<>());
      gamePlan.setItems(new ArrayList<>());
      gamePlan.setSkills(new ArrayList<>());
      runAllParser(gamePlan, fileIn);
      games.put(gamePlan.getName(), gamePlan);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void runAllParser(GamePlan gamePlan, Scanner fileIn) {

    while (fileIn.hasNext()) {
      String line = fileIn.nextLine();

      if (!line.startsWith("//") && !Objects.equals(line, "")) {
        for (GamePlanParser parser : gamePlanParsers) {
          if (parser.parseGamePlan(gamePlan, line, fileIn))
            break;
        }
      }
    }


  }

}
