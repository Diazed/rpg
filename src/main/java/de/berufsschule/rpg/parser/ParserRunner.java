package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ParserRunner {

  Game game;
  List<de.berufsschule.rpg.parser.Parser> parser;

  @Autowired
  public ParserRunner(List<Parser> parser){
    this.parser = parser;
  }

  public void parse(String filename){

    ClassLoader classLoader = getClass().getClassLoader();
    try(Scanner fileIn = new Scanner(new File(classLoader.getResource("file/"+filename+".txt").getFile()))) {
      game = new Game();
      while (fileIn.hasNext()){
        runAllParser(game, fileIn.nextLine());
      }
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }

  private void runAllParser(Game game, String line){
    for (Parser aParser : parser) {
      if (aParser.parse(game, line))
        return;
    }
  }

}
