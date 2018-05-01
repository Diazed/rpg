package de.berufsschule.rpg.parser;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.GamePlan;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.services.DecisionService;
import de.berufsschule.rpg.services.PageService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QuestionPageHandler {

  private PageService pageService;
  private DecisionService decisionService;
  private GamePlan gamePlan;

  public QuestionPageHandler(PageService pageService,
      DecisionService decisionService) {
    this.pageService = pageService;
    this.decisionService = decisionService;
  }

  public GamePlan createQuestionPages(GamePlan gamePlan) {

    this.gamePlan = gamePlan;
    List<Page> pages = new ArrayList<>(this.gamePlan.getPages());
    for (Page page : pages) {
      createQuestionPage(page);
    }
    return this.gamePlan;
  }

  private void createQuestionPage(Page page) {

    List<Decision> decisions = new ArrayList<>(page.getDecisions());
    for (Decision decision : decisions) {
      if (decision.getIsQuestion()) {
        handleQuestion(decision, page);
      }
    }
  }

  private void handleQuestion(Decision question, Page parent) {

    buildNewPage(false, question, parent);
    if (question.getAltAnswer() != null) {
      buildNewPage(true, question, parent);
    }
  }

  private void buildNewPage(boolean altPage, Decision question, Page parent) {

    question.setIsQuestion(false);
    Page builtPage = new Page();
    builtPage.setName(parent.getName());
    List<Decision> newDecisions = createNewDecisions(parent.getDecisions(), question.getId());
    builtPage.getDecisions().addAll(newDecisions);
    if (altPage) {
      builtPage.setStorytext(question.getAltAnswer());
      pageService.savePage(builtPage);
      question.setAltJump(builtPage.getId());
    } else {
      builtPage.setStorytext(question.getAnswer());
      pageService.savePage(builtPage);
      question.setMainJump(builtPage.getId());
    }
    gamePlan.getPages().add(builtPage);
    pageService.savePage(parent);
    createQuestionPage(builtPage);
  }

  private List<Decision> createNewDecisions(List<Decision> decisions, Integer idOfThis) {

    List<Decision> result = new ArrayList<>();

    for (Decision decision : decisions) {
      Decision newDecision = new Decision();
      newDecision.setMainJump(decision.getMainJump());
      newDecision.setAltJump(decision.getAltJump());
      newDecision.setRequiredSkillId(decision.getRequiredSkillId());
      newDecision.setRequiredSkill(decision.getRequiredSkill());
      newDecision.setAltAnswer(decision.getAltAnswer());
      newDecision.setAnswer(decision.getAnswer());
      newDecision.setSkillSuccessLvl(decision.getSkillSuccessLvl());
      newDecision.setProbability(decision.getProbability());
      newDecision.setSkillMinLvl(decision.getSkillMinLvl());
      newDecision.setText(decision.getText());

      if (decision.getId() != idOfThis) {
        newDecision.setWasClicked(decision.getWasClicked());
        newDecision.setIsQuestion(decision.getIsQuestion());
      } else {
        newDecision.setWasClicked(true);
        newDecision.setIsQuestion(false);
      }
      decisionService.saveDecision(newDecision);
      result.add(newDecision);
    }

    return result;
  }
}
