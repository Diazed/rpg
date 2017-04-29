package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Player;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.repositories.SkillRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

  SkillRepository skillRepository;
  PlayerService playerService;

  @Autowired
  public SkillService(SkillRepository skillRepository, PlayerService playerService) {
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
    for (Skill skill : gamePlan.getSkills()) {
      skillRepository.save(skill);
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

        if (neededPoints.equals(1)) {
          Integer skillLevel = skill.getLevel();
          skill.setLevel(skillLevel + 1);
          skill.setGivenSkillPoints(0);
          skill.setNeededSkillPoints(getNeededSkillPoints(skillLevel));
        } else {
          skill.setNeededSkillPoints(neededPoints - 1);
          skill.setGivenSkillPoints(givenPoints + 1);
        }

        player.setSkillPoints(playerSkillPoints - 1);

        saveSkill(skill);
        playerService.savePlayer(player);


      }
    }
  }


  private Integer getNeededSkillPoints(Integer skillLvl) {
    Integer neededSkillPoints = 2;

    for (int i = 0; i < (skillLvl + 1); i++) {
      neededSkillPoints += i * 1;
    }
    return neededSkillPoints;
  }


}
