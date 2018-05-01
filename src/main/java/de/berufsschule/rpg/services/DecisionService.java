package de.berufsschule.rpg.services;

import de.berufsschule.rpg.domain.model.Decision;
import de.berufsschule.rpg.domain.repositories.DecisionRepository;
import de.berufsschule.rpg.eventhandling.possibilityevents.DecisionEvent;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Skill;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DecisionService {

  private List<DecisionEvent> possibilityEvents;
  private DecisionRepository decisionRepository;

  @Autowired
  public DecisionService(List<DecisionEvent> possibilityEvents,
      DecisionRepository decisionRepository) {
    this.possibilityEvents = possibilityEvents;
    this.decisionRepository = decisionRepository;
  }

  public void saveDecision(Decision decision) {
    decisionRepository.save(decision);
  }

  public void runPossibilityEvents(Decision decision, Player player, Page page) {
    for (DecisionEvent possibilityEvent : possibilityEvents) {
      possibilityEvent.event(decision, player, page);
    }
  }

  public Decision getDecision(Integer clickedPossibilityId) {

    return decisionRepository.findOne(clickedPossibilityId);
  }

  public Decision setFlags(Decision decision, Player player) {
    decision.setHasItem(playerOwnsItem(decision, player));
    decision.setHasSkill(playerOwnsSkill(decision, player));
    return decision;
  }

  private boolean playerOwnsSkill(Decision decision, Player player) {
    String requiredSkill = decision.getRequiredSkill();
    if (requiredSkill != null) {
      for (Skill skill : player.getSkills()) {
        if (skill.getName().equals(requiredSkill) && decision.getSkillMinLvl() <= skill
            .getLevel()) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  private boolean playerOwnsItem(Decision decision, Player player) {
    String itemName = decision.getUsedItem();
    if (itemName != null) {
      for (Item item : player.getItems()) {
        if (item.getName().equals(itemName)) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

}
