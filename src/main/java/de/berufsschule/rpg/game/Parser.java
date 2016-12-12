package de.berufsschule.rpg.game;

import de.berufsschule.rpg.item.Item;
import de.berufsschule.rpg.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Parser {

  private ItemService itemService;

  @Autowired
  public Parser(ItemService itemService){
    this.itemService = itemService;
  }

  public Game parser() {

    Game game = new Game();
    List<Page> pages = new ArrayList<Page>();

    try {
      ClassLoader classLoader = getClass().getClassLoader();
      Scanner fileIn = new Scanner(new File(classLoader.getResource("file/neu.txt").getFile()));
      Integer pageCounter = 0;
      Integer decisionCounter = 0;
      Integer itemCounter = 0;

      while (fileIn.hasNextLine()) {
        String line = fileIn.nextLine();
        if (line != "" || !line.startsWith("//")) {

          //PAGE

          if (line.contains("#PAGE")) {
            Page page = new Page();
            List<Decision> decisions = new ArrayList<Decision>();
            List<Item> items = new ArrayList<Item>();
            page.setDecisions(decisions);
            page.setItems(items);
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
            while (!line.contains("#TEXTEND")){
              if (fileIn.hasNextLine()) {
                line = fileIn.nextLine();
                String storytext = getStringBetweenQuotationMarks(line);
                pages.get(pageCounter).setStorytext(storytext);
              }
            }
          }

          if (line.contains("#USEITEM")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String usedItem = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).setUsedItem(usedItem);
            }
          }

          //ITEM

          if (line.contains("#GIVEITEM")) {
            Item item = new Item();
            pages.get(pageCounter).getItems().add(item);
          }

          if (line.contains("#ITEMNAME")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String name = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).getItems().get(itemCounter).setName(name);
            }
          }

          if (line.contains("#ITEMDESCRIPTION")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String description = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).getItems().get(itemCounter).setDescription(description);
            }
          }

          if (line.contains("#ITEMVALUE")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String value = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).getItems().get(itemCounter).setValue(Integer.parseInt(value));
            }
          }

          if (line.contains("#ITEMAMOUNT")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String amount = getStringBetweenQuotationMarks(line);
              pages.get(pageCounter).getItems().get(itemCounter).setAmount(Integer.parseInt(amount));
            }
          }

          if (line.contains("#CONSUMABLE")) {
            pages.get(pageCounter).getItems().get(itemCounter).setConsumable(true);
          }

          if (line.contains("#DRINK")) {
            pages.get(pageCounter).getItems().get(itemCounter).setDrink(true);
          }

          if (line.contains("#FOOD")) {
            pages.get(pageCounter).getItems().get(itemCounter).setDrink(false);
          }

          //ENTSCHEIDUNGEN

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

          if (line.contains("#ITEMNEEDED")) {
            if (fileIn.hasNextLine()) {
              line = fileIn.nextLine();
              String item = getStringBetweenQuotationMarks(line);

              pages.get(pageCounter).getDecisions().get(decisionCounter).setItem(itemService.findItemByName(item));

            }
          }

          //ENDINGS

          if (line.contains("#IEND")) {

            if (itemService.findItemByName(pages.get(pageCounter).getItems().get(itemCounter).getName()) == null)
              itemService.saveNewItem(pages.get(pageCounter).getItems().get(itemCounter));

            itemCounter++;
          }

          if (line.contains("#DEND")) {
            decisionCounter++;
          }

          if (line.contains("#PEND")) {
            decisionCounter = 0;
            itemCounter = 0;
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
    line = line.replace("'", "");

    return line;
  }


}
