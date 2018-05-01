package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.parser.pageparser.ParseStartPage;
import de.berufsschule.rpg.services.PageService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseParserTest {

  private Scanner scanner;
  private BaseParser systemUnderTest;
  private GamePlan gamePlan;
  @Mock
  PageService pageService;

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
    setUpScanner("Test");
    systemUnderTest = new ParseStartPage(pageService);

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

    Decision firstPossibility = new Decision();
    firstPossibility.setText("firstPossibility");
    Decision secondPossibility = new Decision();
    secondPossibility.setText("secondPossibility");

    secondPage.getDecisions().add(firstPossibility);
    secondPage.getDecisions().add(secondPossibility);

    gamePlan.getPages().add(firstPage);
    gamePlan.getPages().add(secondPage);
    gamePlan.getItems().add(firstItem);
    gamePlan.getItems().add(secondItem);
    gamePlan.getSkills().add(firstSkill);
    gamePlan.getSkills().add(secondSkill);

  }

  @After
  public void tearDown() {
    scanner.close();
  }

  @Test
  public void getLastCreatedPage() {
    Page actual = systemUnderTest.getLastCreatedPage(gamePlan);
    Assert.assertEquals("secondPage", actual.getName());
  }

  @Test
  public void getLastCreatedItem() {
    Item actual = systemUnderTest.getLastCreatedItem(gamePlan);
    Assert.assertEquals("secondItem", actual.getName());
  }

  @Test
  public void getLastCreatedSkill() {
    Skill actual = systemUnderTest.getLastCreatedSkill(gamePlan);
    Assert.assertEquals("secondSkill", actual.getName());
  }

  @Test
  public void getLastCreatedPossibility() {
    Decision actual = systemUnderTest.getLastCreatedDecision(gamePlan);
    Assert.assertEquals("secondPossibility", actual.getText());
  }

  @Test
  public void parseIntDefaultsToOne() {
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