package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.*;
import de.berufsschule.rpg.parser.pageparser.ParseStartPage;
import de.berufsschule.rpg.parser.tools.Command;
import de.berufsschule.rpg.services.PageService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseParserTest {

  private Scanner scanner;
  private BaseParser systemUnderTest;
  private GamePlan gamePlan;
  @Mock
  PageService pageService;

  private Page firstPage = new Page();
  private Page secondPage = new Page();
  private Item firstItem = new Item();
  private Item secondItem = new Item();
  private Skill firstSkill = new Skill();
  private Skill secondSkill = new Skill();
  private Decision firstPossibility = new Decision();
  private Decision secondPossibility = new Decision();

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
    setUpScanner("Test");
    systemUnderTest = new ParseStartPage(pageService);
    gamePlan = new GamePlan();

    firstPage.setName("firstPage");
    secondPage.setName("secondPage");
    firstItem.setName("firstItem");
    secondItem.setName("secondItem");
    firstSkill.setName("firstSkill");
    secondSkill.setName("secondSkill");
    firstPossibility.setText("firstPossibility");
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
  public void checkCommand() {
    ParseModel mock = Mockito.mock(ParseModel.class);
    when(mock.getCurrentLine()).thenReturn("  #GAMENAME  ");
    boolean actual = systemUnderTest.checkCommand(mock, Command.GAMENAME);
    assertThat(actual).isTrue();
  }

  @Test
  public void checkCommandRetunsFalseIfNoWantedCommand() {
    ParseModel mock = Mockito.mock(ParseModel.class);
    when(mock.getCurrentLine()).thenReturn("  #ITEMS  ");
    boolean actual = systemUnderTest.checkCommand(mock, Command.GAMENAME);
    assertThat(actual).isFalse();
  }

  @Test
  public void findPageByName() {
    Page actual = systemUnderTest.findPageByName(gamePlan, "firstPage");
    assertThat(actual).isEqualTo(firstPage);
  }

  @Test
  public void findPageByNameReturnsNullWhenNoResultsFound() {
    Page actual = systemUnderTest.findPageByName(gamePlan, "none");
    assertThat(actual).isNull();
  }

  @Test
  public void findSkillByName() {
    Skill actual = systemUnderTest.findSkillByName(gamePlan, "firstSkill");
    assertThat(actual).isEqualTo(firstSkill);
  }

  @Test
  public void findSkillByNameReturnsNullWhenNoResultsFound() {
    Skill actual = systemUnderTest.findSkillByName(gamePlan, "none");
    assertThat(actual).isNull();
  }

  @Test
  public void findItemByName() {
    Item actual = systemUnderTest.findItemByName(gamePlan, "firstItem");
    assertThat(actual).isEqualTo(firstItem);
  }

  @Test
  public void findItemByNameReturnsNullWhenNoResultFound() {
    Item actual = systemUnderTest.findItemByName(gamePlan, "none");
    assertThat(actual).isNull();
  }

  @Test
  public void getLastCreatedPage() {
    Page actual = systemUnderTest.getLastCreatedPage(gamePlan);
    assertThat(actual).isEqualTo(secondPage);
  }

  @Test
  public void getLastCreatedPageReturnsNullWhenNoPagesInGamePlan() {
    gamePlan.getPages().clear();
    Page actual = systemUnderTest.getLastCreatedPage(gamePlan);
    assertThat(actual).isNull();
  }

  @Test
  public void getLastCreatedItem() {
    Item actual = systemUnderTest.getLastCreatedItem(gamePlan);
    assertThat(actual).isEqualTo(secondItem);
  }

  @Test
  public void getLastCreatedItemReturnsNullWhenNoItemsInGamePlan() {
    gamePlan.getItems().clear();
    Item actual = systemUnderTest.getLastCreatedItem(gamePlan);
    assertThat(actual).isNull();
  }

  @Test
  public void getLastCreatedSkill() {
    Skill actual = systemUnderTest.getLastCreatedSkill(gamePlan);
    assertThat(actual).isEqualTo(secondSkill);
  }

  @Test
  public void getLastCreatedSkillReturnsNullWhenNoSkillsInGamePlan() {
    gamePlan.getSkills().clear();
    Skill actual = systemUnderTest.getLastCreatedSkill(gamePlan);
    assertThat(actual).isNull();
  }

  @Test
  public void getLastCreatedDecision() {
    Decision actual = systemUnderTest.getLastCreatedDecision(gamePlan);
    assertThat(actual).isEqualTo(secondPossibility);
  }

  @Test
  public void getLastCreatedDecisionReturnNullWhenPageHasNoDecisions() {
    Page page = systemUnderTest.getLastCreatedPage(gamePlan);
    page.getDecisions().clear();
    Decision actual = systemUnderTest.getLastCreatedDecision(gamePlan);
    assertThat(actual).isNull();
  }

  @Test
  public void parseIntDefaultsToOne() {
    Integer expected = 1;
    Integer actual = systemUnderTest.parseInt(" 5t ");
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void parseIntCutsOffWhitespaces() {
    Integer expected = 5;
    Integer actual = systemUnderTest.parseInt("   5    ");
    assertThat(actual).isEqualTo(expected);
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