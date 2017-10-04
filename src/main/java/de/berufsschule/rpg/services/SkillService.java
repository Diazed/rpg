package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.repositories.SkillRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

  private SkillRepository skillRepository;
  private PlayerService playerService;

  @Autowired
  public SkillService(SkillRepository skillRepository,
      PlayerService playerService) {
    this.skillRepository = skillRepository;
    this.playerService = playerService;
  }

  public Skill getSkillByName(String name) {
    return skillRepository.findByName(name);
  }

  public void saveSkill(Skill skill) {
    skillRepository.save(skill);
  }

  public void persistSkillsFromGamePlan(GamePlan gamePlan) {
    Optional<List<Skill>> skills = Optional.ofNullable(gamePlan.getSkills());
    if (skills.isPresent()) {
      List<Skill> skillList = skills.get();
      if (!skillList.isEmpty()) {
        skillRepository.save(skillList);
      }
    }
  }

  public void useSkillPoint(String skillname, Player player) {

    if (!player.getSkillPoints().equals(0)) {
      Optional<Skill> skillOptional = Optional.ofNullable(getSkillByName(skillname));
      if (skillOptional.isPresent()) {
        Skill skill = skillOptional.get();

        Integer givenPoints = skill.getGivenSkillPoints();
        Integer neededPoints = skill.getNeededSkillPoints();
        Integer playerSkillPoints = player.getSkillPoints();

        if (givenPoints + 1 >= neededPoints) {
          Integer skillLevel = skill.getLevel();
          skill.setLevel(skillLevel + 1);
          skill.setGivenSkillPoints(0);
          skill.setNeededSkillPoints(getNeededSkillPoints(skillLevel + 1));
        } else {
          skill.setGivenSkillPoints(givenPoints + 1);
        }

        player.setSkillPoints(playerSkillPoints - 1);
        calculateSkillProgress(skill);
        saveSkill(skill);
        playerService.savePlayer(player);
      }
    }
  }

  private void calculateSkillProgress(Skill skill) {

    Double hundret = skill.getNeededSkillPoints().doubleValue();
    Double one = hundret / 100d;
    Double currentPoints = skill.getGivenSkillPoints().doubleValue();
    Double percent = currentPoints / one;
    skill.setProgress(percent.intValue());
  }

  private Integer getNeededSkillPoints(Integer skillLvl) {
    Double neededSkillPoints = 0d;

    if (skillLvl <= 1) {
      neededSkillPoints = 1d;
    }

    for (int i = 0; i < skillLvl; i++) {
      neededSkillPoints += i * 2d;
    }
    neededSkillPoints = Math.floor(neededSkillPoints);
    return neededSkillPoints.intValue();
  }
}
