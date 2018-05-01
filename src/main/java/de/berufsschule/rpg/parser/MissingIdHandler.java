package de.berufsschule.rpg.parser;


import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.ParseModel;
import de.berufsschule.rpg.services.DecisionService;
import de.berufsschule.rpg.services.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MissingIdHandler extends BaseParser {

  private DecisionService decisionService;

  public MissingIdHandler(DecisionService decisionService) {
    this.decisionService = decisionService;
  }

  public void fillMissingJumpIds(PageService pageService, ParseModel parseModel) {
    for (Decision decision : parseModel.getUncompleteDecisions()) {
      fillMainJump(pageService, parseModel, decision);
      fillAltJump(pageService, parseModel, decision);
    }
  }

  private void fillAltJump(PageService pageService, ParseModel parseModel, Decision decision) {
    if (decision.getAltJump() == null && decision.getAltJumpName() != null) {
      Page altJump = findPageByName(parseModel.getGamePlan(), decision.getAltJumpName());
      if (altJump == null) {
        log.warn(
            "Alt JumpPage " + decision.getAltJumpName() + " konnte nicht gefunden werden.");
      } else {
        decision.setAltJump(altJump.getId());
        decisionService.saveDecision(decision);
        pageService.savePage(altJump);
      }
    }
  }

  private void fillMainJump(PageService pageService, ParseModel parseModel, Decision decision) {
    if (decision.getMainJump() == null) {
      Page mainJump = findPageByName(parseModel.getGamePlan(), decision.getMainJumpName());
      if (mainJump == null) {
        log.warn("JumpPage " + decision.getMainJumpName() + " konnte nicht gefunden werden.");
      } else {
        decision.setMainJump(mainJump.getId());
        decisionService.saveDecision(decision);
        pageService.savePage(mainJump);
      }
    }
  }

}
