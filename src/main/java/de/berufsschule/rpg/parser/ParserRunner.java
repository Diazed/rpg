package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.game.Game;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
@Getter
@Setter
public class ParserRunner {

  Game game;
  List<de.berufsschule.rpg.parser.Parser> parser;

  @Autowired
  public ParserRunner(List<Parser> parser){
    this.parser = parser;
  }

  public void parse(){

    String filename = "neu";

    ClassLoader classLoader = getClass().getClassLoader();
    try(Scanner fileIn = new Scanner(new File(classLoader.getResource("file/"+filename+".txt").getFile()))) {
      game = new Game();
      game.setPages(new ArrayList<>());
      runAllParser(game, fileIn);
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }

  private void runAllParser(Game game, Scanner fileIn){

    while (fileIn.hasNext()){
      String line = fileIn.nextLine();

      if (!line.startsWith("//") && !Objects.equals(line, ""))
      {
        for (Parser aParser : parser) {
          if (aParser.parse(game, line, fileIn))
            break;
        }
      }
    }


  }

}
