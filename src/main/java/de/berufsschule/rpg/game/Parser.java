package de.berufsschule.rpg.game;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Parser {

  public static Game parser() {

    Game game = new Game();
    List<Page> pages = new ArrayList<Page>();

    try {
      Scanner fileIn = new Scanner(new File("C:\\test.txt"));
      Integer pageCounter = 0;
      Integer decisionCounter = 0;

      while (fileIn.hasNextLine()) {
        String line = fileIn.nextLine();
        if (line != "" || !line.startsWith("//")) {
          if (line.contains("#PAGE")) {
            Page page = new Page();
            List<Decision> decisions = new ArrayList<Decision>();
            page.setDecisions(decisions);
            pages.add(page);
          }

          if (line.contains("#NAME")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String name = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).setName(name);
            }
          }

          if (line.contains("#STORYTEXT")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String storytext = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).setStorytext(storytext);
            }
          }

          if (line.contains("#DECISION")) {
            Decision decision = new Decision();
            pages.get(pageCounter).getDecisions().add(decision);
          }

          if (line.contains("#JUMP")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String jump = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).getDecisions().get(decisionCounter).setJump(jump);
            }
          }

          if (line.contains("#TEXT")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String text = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).getDecisions().get(decisionCounter).setText(text);
            }
          }

          if (line.contains("#DEND")) {
            decisionCounter++;
          }

          if (line.contains("#PEND")) {
            decisionCounter = 0;
            pageCounter++;
          }
        }

      }
      fileIn.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    game.setPages(pages);
    return game;
  }

  private static String getStringBetweenQuotationMarks(String line) {
    String string = "KEIN TEXT IN \"\" ANGEGEBEN";
    line = line.replace("\t", "");
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == '\'') {
        i++;
        char text[] = new char[line.length() - i];
        line.getChars(i, line.length() - 1, text, 1);
        string = String.valueOf(text);
        break;
      }
    }
    return string;
  }


}
