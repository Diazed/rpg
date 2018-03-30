package de.berufsschule.rpg.services;

import de.berufsschule.rpg.eventhandling.possibilityevents.PossibilityEvent;
import de.berufsschule.rpg.domain.model.Item;
import de.berufsschule.rpg.domain.model.Page;
import de.berufsschule.rpg.domain.model.Player;
import de.berufsschule.rpg.domain.model.Possibility;
import de.berufsschule.rpg.domain.model.Skill;
import de.berufsschule.rpg.domain.repositories.PossibilityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PossibilityService {

  private List<PossibilityEvent> possibilityEvents;
  private PossibilityRepository possibilityRepository;

  @Autowired
  public PossibilityService(List<PossibilityEvent> possibilityEvents,
      PossibilityRepository possibilityRepository) {
    this.possibilityEvents = possibilityEvents;
    this.possibilityRepository = possibilityRepository;
  }

  public void savePossibility(Possibility possibility) {
    possibilityRepository.save(possibility);
  }

  public void runPossibilityEvents(Possibility possibility, Player player, Page page) {
    for (PossibilityEvent possibilityEvent : possibilityEvents) {
      possibilityEvent.event(possibility, player, page);
    }
  }

  public Possibility getPossibility(Integer clickedPossibilityId) {

    return possibilityRepository.findOne(clickedPossibilityId);
  }

  public Possibility setFlags(Possibility possibility, Player player) {
    possibility.setHasItem(playerOwnsItem(possibility, player));
    possibility.setHasSkill(playerOwnsSkill(possibility, player));
    return possibility;
  }

  private boolean playerOwnsSkill(Possibility possibility, Player player) {
    String requiredSkill = possibility.getRequiredSkill();
    if (requiredSkill != null) {
      for (Skill skill : player.getSkills()) {
        if (skill.getName().equals(requiredSkill) && possibility.getSkillMinLvl() <= skill
            .getLevel()) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  private boolean playerOwnsItem(Possibility possibility, Player player) {
    String itemname = possibility.getUsedItem();
    if (itemname != null) {
      for (Item item : player.getItems()) {
        if (item.getName().equals(itemname)) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

}
