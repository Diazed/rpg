package de.berufsschule.rpg.services;

import de.berufsschule.rpg.model.GamePlan;
import de.berufsschule.rpg.model.Skill;
import de.berufsschule.rpg.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill getSkillByName(String name){
        return skillRepository.findByName(name);
    }

    public void persistSkillsFromGamePlan(GamePlan gamePlan){
        for (Skill skill : gamePlan.getSkills()){
            skillRepository.save(skill);
        }
    }

}
