package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.model.Decision;
import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Item;
import de.berufsschule.rpg.model.Page;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.parser.gameplanparser.ParseStartPage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseParserTest {

  private Scanner scanner;
  private BaseParser systemUnderTest;
  private GamePlan gamePlan;

  @Before
  public void setUp() throws Exception {

    setUpScanner("Test");
    systemUnderTest = new ParseStartPage();

    gamePlan = new GamePlan();

    Page firstPage = new Page();
    firstPage.setName("firstPage");
    Page secondPage = new Page();
    secondPage.setName("secondPage");

    Item firstItem = new Item();
    firstItem.setName("firstItem");
    Item secondItem = new Item();
    secondItem.setName("secondItem");

    Skill firstSkill = new Skill();
    firstSkill.setName("firstSkill");
    Skill secondSkill = new Skill();
    secondSkill.setName("secondSkill");

    Decision firstDecision = new Decision();
    firstDecision.setText("firstDecision");
    Decision secondDecision = new Decision();
    secondDecision.setText("secondDecision");

    secondPage.getDecisions().add(firstDecision);
    secondPage.getDecisions().add(secondDecision);

    gamePlan.getPages().add(firstPage);
    gamePlan.getPages().add(secondPage);
    gamePlan.getItems().add(firstItem);
    gamePlan.getItems().add(secondItem);
    gamePlan.getSkills().add(firstSkill);
    gamePlan.getSkills().add(secondSkill);

  }

  @After
  public void tearDown() throws Exception {
    scanner.close();
  }

  @Test
  public void getNextLine() throws Exception {
    setUpScanner("'Test'");
    String actual = systemUnderTest.getNextLine(scanner);
    Assert.assertEquals("Test", actual);
    actual = systemUnderTest.getNextLine(scanner);
    Assert.assertEquals("", actual);
  }

  @Test
  public void getLastCreatedPage() throws Exception {
    Page actual = systemUnderTest.getLastCreatedPage(gamePlan);
    Assert.assertEquals("secondPage", actual.getName());
  }

  @Test
  public void getLastCreatedItem() throws Exception {
    Item actual = systemUnderTest.getLastCreatedItem(gamePlan);
    Assert.assertEquals("secondItem", actual.getName());
  }

  @Test
  public void getLastCreatedSkill() throws Exception {
    Skill actual = systemUnderTest.getLastCreatedSkill(gamePlan);
    Assert.assertEquals("secondSkill", actual.getName());
  }

  @Test
  public void getLastCreatedDecision() throws Exception {
    Decision actual = systemUnderTest.getLastCreatedDecision(gamePlan);
    Assert.assertEquals("secondDecision", actual.getText());
  }

  @Test
  public void parseIntDefaultsToOne() throws Exception {
    Integer expected = 1;
    Integer actual = systemUnderTest.parseInt(" 5t ");
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void parseIntCutsOffWhitespaces() {
    Integer expected = 5;
    Integer actual = systemUnderTest.parseInt("   5    ");
    Assert.assertEquals(expected, actual);
  }

  private void setUpScanner(String content) {
    if (scanner != null) {
      scanner.close();
      scanner = null;
    }
    InputStream in = new ByteArrayInputStream(content.getBytes());
    scanner = new Scanner(in);
  }

}