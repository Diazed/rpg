package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Game;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@Getter
@Setter
public class ParserRunner {

  HashMap<String, Game> games = new HashMap<String, Game>();

  List<Parser> parser;

  @Autowired
  public ParserRunner(List<Parser> parser) {
    this.parser = parser;
  }

  public void parse(String filename) {

    ClassLoader classLoader = getClass().getClassLoader();
    try (Scanner fileIn = new Scanner(new File(classLoader.getResource("file/" + filename).getFile()))) {
      if (fileIn.hasNextLine()){
        if (!fileIn.nextLine().contains("#RPG")){
          return;
        }
      }else {
        return;
      }
      Game game = new Game();
      game.setPages(new ArrayList<>());
      runAllParser(game, fileIn);
      games.put(game.getName(), game);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void runAllParser(Game game, Scanner fileIn) {

    while (fileIn.hasNext()) {
      String line = fileIn.nextLine();

      if (!line.startsWith("//") && !Objects.equals(line, "")) {
        for (Parser aParser : parser) {
          if (aParser.parse(game, line, fileIn))
            break;
        }
      }
    }


  }

}
